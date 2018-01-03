package me.javaroad.sdk.wechat.mp;

import me.javaroad.sdk.wechat.mp.config.WeChatMpProperties;
import me.javaroad.sdk.wechat.mp.controller.WeChatCallBackController;
import me.javaroad.sdk.wechat.mp.support.DefaultMessageDispatcher;
import me.javaroad.sdk.wechat.mp.support.MessageDispatcher;
import me.javaroad.sdk.wechat.mp.support.handler.DefaultMessageHandlerFactory;
import me.javaroad.sdk.wechat.mp.support.handler.MessageHandlerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
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

    @Bean
    @ConditionalOnMissingBean(MessageDispatcher.class)
    public MessageDispatcher defaultMessageDispatcher(MessageHandlerFactory messageHandlerFactory,
        ApplicationContext applicationContext) {
        return new DefaultMessageDispatcher(messageHandlerFactory, applicationContext);
    }

    @Bean
    @ConditionalOnMissingBean(MessageHandlerFactory.class)
    public MessageHandlerFactory defaultMessageHandlerFactory() {
        return new DefaultMessageHandlerFactory();
    }

}
