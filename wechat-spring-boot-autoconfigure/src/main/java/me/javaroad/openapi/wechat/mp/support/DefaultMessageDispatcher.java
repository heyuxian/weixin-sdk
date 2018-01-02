package me.javaroad.openapi.wechat.mp.support;

import lombok.RequiredArgsConstructor;
import me.javaroad.openapi.wechat.mp.event.ReceiveMessageEvent;
import me.javaroad.openapi.wechat.mp.model.message.Message;
import me.javaroad.openapi.wechat.mp.model.message.ResponseMessage;
import me.javaroad.openapi.wechat.mp.support.handler.MessageHandler;
import me.javaroad.openapi.wechat.mp.support.handler.MessageHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author heyx
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultMessageDispatcher implements MessageDispatcher {

    private final MessageHandlerFactory messageHandlerFactory;
    private final ApplicationContext applicationContext;

    @Override
    public ResponseMessage dispatch(Message message) {
        applicationContext.publishEvent(new ReceiveMessageEvent(message));
        MessageHandler messageHandler = messageHandlerFactory.getHandler(message.getClass().getCanonicalName());
        return messageHandler.handleMessage(message);
    }
}
