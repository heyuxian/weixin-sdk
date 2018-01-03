package me.javaroad.sdk.wechat.exception;

import lombok.NoArgsConstructor;

/**
 * @author heyx
 */
@NoArgsConstructor
public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
