package me.javaroad.openapi.wechat.utils;

import java.security.MessageDigest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EncryptUtils {

    public static String sha1(String source) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(source.getBytes());
            byte[] digest = md.digest();

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                String shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    builder.append(0);
                }
                builder.append(shaHex);
            }
            return builder.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
