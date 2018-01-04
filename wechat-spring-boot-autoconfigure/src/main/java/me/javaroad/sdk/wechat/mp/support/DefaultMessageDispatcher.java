package me.javaroad.sdk.wechat.mp.support;

import lombok.RequiredArgsConstructor;
import me.javaroad.sdk.wechat.mp.event.ReceiveMessageEvent;
import me.javaroad.sdk.wechat.mp.model.message.Message;
import me.javaroad.sdk.wechat.mp.model.message.ResponseMessage;
import me.javaroad.sdk.wechat.mp.support.handler.MessageHandler;
import me.javaroad.sdk.wechat.mp.support.handler.MessageHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @author heyx
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultMessageDispatcher implements MessageDispatcher {

    private final MessageHandlerFactory messageHandlerFactory;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @SuppressWarnings("unchecked")
    public ResponseMessage dispatch(Message message) {
        eventPublisher.publishEvent(new ReceiveMessageEvent(message));
        MessageHandler messageHandler = messageHandlerFactory.getHandler(message.getClass().getCanonicalName());
        return messageHandler.handleMessage(message);
    }
}
