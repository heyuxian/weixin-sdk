package me.javaroad.openapi.wechat.mp.support;

import me.javaroad.openapi.wechat.mp.model.message.Message;
import me.javaroad.openapi.wechat.mp.model.message.ResponseMessage;

/**
 * @author heyx
 */
public interface MessageDispatcher {
    ResponseMessage dispatch(Message message);
}
