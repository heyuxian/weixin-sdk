package me.javaroad.sdk.wechat.mp.support;

import me.javaroad.sdk.wechat.mp.model.message.Message;
import me.javaroad.sdk.wechat.mp.model.message.ResponseMessage;

/**
 * @author heyx
 */
public interface MessageDispatcher {
    ResponseMessage dispatch(Message message);
}
