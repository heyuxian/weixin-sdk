package me.javaroad.sdk.wechat.exception;

import lombok.NoArgsConstructor;

/**
 * @author heyx
 */
@NoArgsConstructor
public class WeChatApiException extends RuntimeException {

    public WeChatApiException(String message) {
        super(message);
    }

}
