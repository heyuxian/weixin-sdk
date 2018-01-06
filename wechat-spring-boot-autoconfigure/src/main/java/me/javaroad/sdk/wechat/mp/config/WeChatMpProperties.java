package me.javaroad.sdk.wechat.mp.config;

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
    private Security security;
    private Endpoint endpoint;
    private Log log;

    @Getter
    @Setter
    public static class Security {

        private String token;
        private String encodingAesKey;
        private Integer refreshTokenThreshold = 5 * 60;
    }

    @Getter
    @Setter
    public static class Endpoint {

        private String callbackUrl;
        private String uploadImageUrl = "https://api.weixin.qq.com/cgi-bin/media/uploadimg";
    }


    @Getter
    @Setter
    public static class OAuth {

        private String appid;
        private String secret;
        private String grantType = "client_credential";
        private String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token";

        public String buildTokenUrl() {
            return tokenUrl
                + "?grant_type=" + grantType
                + "&appid=" + appid
                + "&secret=" + secret;
        }
    }

    @Getter
    @Setter
    public static class Log {
        private Boolean logPostParam = true;
    }
}
