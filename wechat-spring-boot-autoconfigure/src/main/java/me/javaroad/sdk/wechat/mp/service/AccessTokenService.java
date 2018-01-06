package me.javaroad.sdk.wechat.mp.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.javaroad.sdk.wechat.exception.WeChatApiException;
import me.javaroad.sdk.wechat.mp.config.WeChatMpProperties;
import me.javaroad.sdk.wechat.mp.http.WeChatApiRequest;
import me.javaroad.sdk.wechat.mp.model.AccessToken;
import me.javaroad.sdk.wechat.mp.store.TokenStore;
import me.javaroad.sdk.wechat.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;

/**
 * @author heyx
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccessTokenService {

    private final TokenStore tokenStore;
    private final WeChatApiRequest weChatApiRequest;
    private final WeChatMpProperties mpProperties;

    public String buildTokenUrl(String url) {
        AccessToken accessToken = getAccessToken();
        if (!url.contains("?")) {
            return url + "?access_token=" + accessToken.getAccessToken();
        }
        return url + "&access_token=" + accessToken.getAccessToken();
    }

    AccessToken getAccessToken() {
        AccessToken accessToken = tokenStore.readAccessToken();
        if (!needRefresh(accessToken)) {
            return accessToken;
        }
        return refreshToken();
    }

    boolean needRefresh(AccessToken accessToken) {
        if (Objects.isNull(accessToken)) {
            return true;
        }
        long currentSecond = DateUtils.currentSecond();
        return (accessToken.getRefreshIn() + accessToken.getExpiresIn() + mpProperties.getSecurity()
            .getRefreshTokenThreshold()) > currentSecond;
    }

    private AccessToken refreshToken() {
        try {
            AccessToken accessToken = weChatApiRequest.get(mpProperties.getAuth().buildTokenUrl(), AccessToken.class);
            if (Objects.isNull(accessToken)) {
                throw new WeChatApiException("get access token failed");
            }
            Long currentSecond = DateUtils.currentSecond();
            accessToken.setRefreshIn(currentSecond);
            tokenStore.storeAccessToken(accessToken);
            return accessToken;
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
            throw new WeChatApiException(e.getMessage());
        }
    }
}
