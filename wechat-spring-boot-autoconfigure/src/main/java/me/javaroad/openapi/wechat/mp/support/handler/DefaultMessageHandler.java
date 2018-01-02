package me.javaroad.openapi.wechat.mp.support.handler;

import me.javaroad.openapi.wechat.mp.event.ReceiveMessageEvent;
import me.javaroad.openapi.wechat.mp.model.message.EmptyResponseMessage;
import me.javaroad.openapi.wechat.mp.model.message.Message;
import me.javaroad.openapi.wechat.mp.model.message.ResponseMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author heyx
 */
@Component
@ConditionalOnMissingBean(MessageHandler.class)
public class DefaultMessageHandler extends AbstractMessageHandler {

    private final ApplicationContext applicationContext;

    public DefaultMessageHandler(MessageHandlerFactory messageHandlerFactory, ApplicationContext applicationContext) {
        super(messageHandlerFactory);
        this.applicationContext = applicationContext;
    }

    public ResponseMessage handleMessage(Message message) {
        applicationContext.publishEvent(new ReceiveMessageEvent(message));
        return new EmptyResponseMessage();
    }
}
