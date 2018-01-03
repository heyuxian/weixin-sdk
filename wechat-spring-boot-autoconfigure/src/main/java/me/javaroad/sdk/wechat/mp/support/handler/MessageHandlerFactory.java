package me.javaroad.sdk.wechat.mp.support.handler;

/**
 * @author heyx
 */
public interface MessageHandlerFactory {

    void register(String name, MessageHandler messageHandler);

    MessageHandler getHandler(String name);
}
