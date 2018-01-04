package me.javaroad.sdk.wechat.mp.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import me.javaroad.sdk.wechat.exception.WeChatApiException;
import me.javaroad.sdk.wechat.mp.config.WeChatMpProperties;
import me.javaroad.sdk.wechat.mp.model.AccessToken;
import me.javaroad.sdk.wechat.mp.store.TokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @author heyx
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccessTokenService {

    private final TokenStore tokenStore;
    private final RestTemplate restTemplate;
    private final WeChatMpProperties mpProperties;

    public AccessToken getAccessToken() {
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
        long currentSecond = System.currentTimeMillis() / 1000;
        return (accessToken.getRefreshIn() + accessToken.getExpiresIn() + mpProperties.getSecurity()
            .getRefreshTokenThreshold()) > currentSecond;
    }

    private AccessToken refreshToken() {
        try {
            ResponseEntity<AccessToken> responseEntity = restTemplate
                .getForEntity(mpProperties.getAuth().buildTokenUrl(), AccessToken.class);

            if (!Objects.equals(responseEntity.getStatusCode(), HttpStatus.OK)) {
                throw new WeChatApiException("get access token failed");
            }
            AccessToken accessToken = responseEntity.getBody();
            Long currentSecond = System.currentTimeMillis() / 1000;
            accessToken.setRefreshIn(currentSecond);
            tokenStore.storeAccessToken(accessToken);
            return accessToken;
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new WeChatApiException(e.getMessage());
        }
    }
}
