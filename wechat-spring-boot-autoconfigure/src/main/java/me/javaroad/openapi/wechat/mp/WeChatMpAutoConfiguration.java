package me.javaroad.openapi.wechat.mp;

import me.javaroad.openapi.wechat.mp.config.WeChatMpProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author heyx
 */
@Configuration
@EnableConfigurationProperties(WeChatMpProperties.class)
public class WeChatMpAutoConfiguration {

}
