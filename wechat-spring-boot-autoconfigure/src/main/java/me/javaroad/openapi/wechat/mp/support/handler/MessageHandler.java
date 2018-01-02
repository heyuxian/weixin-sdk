package me.javaroad.openapi.wechat.mp.support.handler;

import me.javaroad.openapi.wechat.mp.model.message.Message;
import me.javaroad.openapi.wechat.mp.model.message.ResponseMessage;

/**
 * @author heyx
 */
public interface MessageHandler<T extends Message> {

    ResponseMessage handleMessage(T message);
}
