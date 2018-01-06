package me.javaroad.sdk.wechat.mp.service;

import static org.assertj.core.api.Assertions.assertThat;

import me.javaroad.sdk.wechat.mp.model.AccessToken;
import me.javaroad.test.BaseSpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.IfProfileValue;

/**
 * @author heyx
 */
@IfProfileValue(name = "spring.profiles.active", value = "test")
public class AccessTokenServiceTest extends BaseSpringTest {

    @Autowired
    private AccessTokenService accessTokenService;

    @Test
    public void getAccessToken() throws Exception {
        AccessToken accessToken = accessTokenService.getAccessToken();
        assertThat(accessToken).isNotNull();
    }

    @Test
    public void needRefresh() {
        AccessToken accessToken = new AccessToken();
        accessToken.setExpiresIn(5 * 60);
        accessToken.setRefreshIn(System.currentTimeMillis() / 1000);
        boolean needRefresh = accessTokenService.needRefresh(accessToken);
        assertThat(needRefresh).isTrue();
    }

}