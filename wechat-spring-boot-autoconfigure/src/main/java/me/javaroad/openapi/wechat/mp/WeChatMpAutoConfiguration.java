package me.javaroad.openapi.wechat.mp;

import lombok.RequiredArgsConstructor;
import me.javaroad.openapi.wechat.mp.config.WeChatMpProperties;
import me.javaroad.openapi.wechat.mp.controller.WeChatCallBackController;
import me.javaroad.openapi.wechat.mp.support.MessageDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author heyx
 */
@Configuration
@EnableConfigurationProperties(WeChatMpProperties.class)
public class WeChatMpAutoConfiguration {

    @Bean
    public WeChatCallBackController weChatCallBackController() {
        return new WeChatCallBackController();
    }
}
