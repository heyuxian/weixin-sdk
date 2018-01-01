package me.javaroad.openapi.wechat.mp.service;

import lombok.RequiredArgsConstructor;
import me.javaroad.openapi.wechat.mp.event.ReceiveMessageEvent;
import me.javaroad.openapi.wechat.mp.model.message.BaseMessage;
import me.javaroad.openapi.wechat.mp.model.message.BaseResponseMessage;
import me.javaroad.openapi.wechat.mp.model.message.EmptyResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author heyx
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@ConditionalOnMissingBean(MessageHandler.class)
public class DefaultMessageHandler implements MessageHandler {

    private final ApplicationContext applicationContext;

    public BaseResponseMessage handleMessage(BaseMessage message) {
        applicationContext.publishEvent(new ReceiveMessageEvent(message));
        return new EmptyResponseMessage();
    }
}
