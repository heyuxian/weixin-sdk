package me.javaroad.openapi.wechat.utils;

import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author heyx
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUtils {

    public static String sign(Object... params) {
        String sortedStr = Arrays.stream(params).map(String::valueOf).sorted().collect(Collectors.joining());
        return EncryptUtils.sha1(sortedStr);
    }

    public void verify() {

    }
}
