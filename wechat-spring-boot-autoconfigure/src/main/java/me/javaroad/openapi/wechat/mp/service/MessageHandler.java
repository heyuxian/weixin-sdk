package me.javaroad.openapi.wechat.mp.service;

import me.javaroad.openapi.wechat.mp.model.message.BaseMessage;
import me.javaroad.openapi.wechat.mp.model.message.BaseResponseMessage;

/**
 * @author heyx
 */
public interface MessageHandler {

    BaseResponseMessage handleMessage(BaseMessage message);
}
