package me.javaroad.sdk.wechat.mp.handler;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

/**
 * @author heyx
 */
@Component
public class DefaultMessageHandlerFactory implements MessageHandlerFactory {

    private final Map<String, MessageHandler> messageHandlerMap = new ConcurrentHashMap<>();

    public void register(String name, MessageHandler messageHandler) {
        messageHandlerMap.put(name, messageHandler);
    }

    public MessageHandler getHandler(String name) {
        MessageHandler messageHandler = messageHandlerMap.get(name);
        if (Objects.isNull(messageHandler)) {
            throw new IllegalArgumentException("Unsupported message, name=[" + name + "]");
        }
        return messageHandler;
    }
}
