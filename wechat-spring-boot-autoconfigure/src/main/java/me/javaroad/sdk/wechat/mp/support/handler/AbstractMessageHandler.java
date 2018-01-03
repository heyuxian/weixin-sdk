package me.javaroad.sdk.wechat.mp.support.handler;

import java.util.Objects;
import me.javaroad.sdk.wechat.mp.model.message.Message;
import me.javaroad.sdk.wechat.utils.ReflectUtils;

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
