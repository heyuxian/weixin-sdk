package me.javaroad.openapi.wechat.utils;

import org.junit.Test;

/**
 * @author heyx
 */
public class SignUtilsTest {

    @Test
    public void sign() throws Exception {
        System.out.println(SignUtils.sign("b", "a", "c", "1"));
    }

}