package me.javaroad.openapi.wechat.mp.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author heyx
 */
@Getter
@Setter
@ConfigurationProperties("weixin.mp")
public class WeChatMpProperties {

    private OAuth auth;
    private Client client;

    @Getter
    @Setter
    public static class Client {

        private String token;
    }

    @Getter
    @Setter
    public static class OAuth {

        private String appid;
        private String grantType = "client_credential";
        private String secret;
        private String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token";

        public String buildTokenUrl() {
            return tokenUrl
                + "?grant_type=" + grantType
                + "&appid=" + appid
                + "&secret=" + secret;
        }
    }
}
