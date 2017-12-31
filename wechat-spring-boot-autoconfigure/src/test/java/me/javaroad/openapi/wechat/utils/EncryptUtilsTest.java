package me.javaroad.openapi.wechat.utils;

import org.junit.Test;

/**
 * @author heyx
 */
public class EncryptUtilsTest {

    @Test
    public void sha1() throws Exception {
        System.out.println(EncryptUtils.sha1("aaaa"));
    }

}