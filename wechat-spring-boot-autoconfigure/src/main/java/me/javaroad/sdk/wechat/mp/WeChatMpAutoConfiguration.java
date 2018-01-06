package me.javaroad.sdk.wechat.mp;

import me.javaroad.sdk.wechat.mp.config.WeChatMpProperties;
import me.javaroad.sdk.wechat.mp.controller.WeChatCallBackController;
import me.javaroad.sdk.wechat.mp.http.WeChatApiRequest;
import me.javaroad.sdk.wechat.mp.service.AccessTokenService;
import me.javaroad.sdk.wechat.mp.store.InMemoryTokenStore;
import me.javaroad.sdk.wechat.mp.store.TokenStore;
import me.javaroad.sdk.wechat.mp.support.DefaultMessageDispatcher;
import me.javaroad.sdk.wechat.mp.support.MessageDispatcher;
import me.javaroad.sdk.wechat.mp.support.handler.DefaultMessageHandlerFactory;
import me.javaroad.sdk.wechat.mp.support.handler.MessageHandlerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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
    public MessageDispatcher messageDispatcher(MessageHandlerFactory messageHandlerFactory,
        ApplicationContext applicationContext) {
        return new DefaultMessageDispatcher(messageHandlerFactory, applicationContext);
    }

    @Bean
    public AccessTokenService accessTokenService(TokenStore tokenStore,
        WeChatApiRequest weChatApiRequest, WeChatMpProperties mpProperties) {
        return new AccessTokenService(tokenStore, weChatApiRequest, mpProperties);
    }

    @Bean
    public WeChatApiRequest weChatApiRequest(RestTemplate restTemplate, WeChatMpProperties mpProperties) {
        return new WeChatApiRequest(restTemplate, mpProperties);
    }
    @Bean
    @ConditionalOnMissingBean(MessageHandlerFactory.class)
    public MessageHandlerFactory messageHandlerFactory() {
        return new DefaultMessageHandlerFactory();
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
