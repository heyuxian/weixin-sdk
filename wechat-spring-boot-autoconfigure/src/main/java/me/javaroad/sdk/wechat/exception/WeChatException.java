package me.javaroad.sdk.wechat.exception;

/**
 * @author heyx
 */
public class WeChatException extends RuntimeException {

    public WeChatException() {
        super();
    }

    public WeChatException(String message) {
        super(message);
    }

    public WeChatException(String message, Throwable cause) {
        super(message, cause);
    }
}
