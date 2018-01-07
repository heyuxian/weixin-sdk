package me.javaroad.sdk.wechat.mp.handler;

import me.javaroad.sdk.wechat.mp.model.message.Message;
import me.javaroad.sdk.wechat.mp.model.message.ResponseMessage;

/**
 * @author heyx
 */
public interface MessageHandler<T extends Message> {

    ResponseMessage handleMessage(T message);
}
