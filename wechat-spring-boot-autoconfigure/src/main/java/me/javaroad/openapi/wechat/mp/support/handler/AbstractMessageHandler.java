package me.javaroad.openapi.wechat.mp.support.handler;

import java.util.Objects;
import me.javaroad.openapi.wechat.mp.model.message.Message;
import me.javaroad.openapi.wechat.utils.ReflectUtils;

/**
 * @author heyx
 */
public abstract class AbstractMessageHandler<T extends Message> implements MessageHandler<T> {

    public AbstractMessageHandler(MessageHandlerFactory messageHandlerFactory) {
        Class clazz = ReflectUtils.findParameterizedType(getClass(), 0);
        if (Objects.isNull(clazz)) {
            throw new IllegalStateException("init MessageHandler failed");
        }
        messageHandlerFactory.register(clazz.getCanonicalName(), this);
    }
}
