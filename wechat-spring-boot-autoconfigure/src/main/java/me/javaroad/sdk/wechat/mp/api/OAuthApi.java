package me.javaroad.sdk.wechat.mp.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.javaroad.sdk.wechat.mp.config.WeChatMpProperties;
import me.javaroad.sdk.wechat.mp.model.AccessTokenResponse;
import me.javaroad.sdk.wechat.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author heyx
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class OAuthApi {

    private final WeChatMpProperties weChatMpProperties;

    public AccessTokenResponse getAccessToken() {
        return HttpUtils.get(weChatMpProperties.getAuth().buildTokenUrl(), AccessTokenResponse.class);
    }
}
