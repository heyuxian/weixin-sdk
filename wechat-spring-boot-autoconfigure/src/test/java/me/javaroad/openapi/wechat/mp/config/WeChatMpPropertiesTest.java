package me.javaroad.openapi.wechat.mp.config;

import static org.assertj.core.api.Assertions.assertThat;

import me.javaroad.openapi.wechat.BaseSpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author heyx
 */
public class WeChatMpPropertiesTest extends BaseSpringTest {

    @Autowired
    private WeChatMpProperties weChatMpProperties;

    @Test
    public void testConfig() {
        assertThat(weChatMpProperties.getAuth().getAppid()).isEqualTo("appid");
        assertThat(weChatMpProperties.getAuth().getGrantType()).isEqualTo("client_credential");
        assertThat(weChatMpProperties.getAuth().buildTokenUrl())
            .isEqualTo("https://api.weixin.qq.com/cgi-bin/token&grantType=client_credential&appid=appid&secret=secret");
    }
}
