package me.javaroad.sdk.wechat.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author heyx
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

    public static long currentSecond() {
        return System.currentTimeMillis() / 1000;
    }
}
