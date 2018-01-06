package me.javaroad.sdk.wechat.utils;

import me.javaroad.sdk.wechat.mp.config.WeChatMpProperties;
import me.javaroad.test.BaseSpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author heyx
 */
public class SecurityUtilsTest extends BaseSpringTest {

    @Autowired
    private WeChatMpProperties properties;

    @Test
    public void sign() throws Exception {
        System.out.println(SecurityUtils.sign("b", "a", "c", "1"));
    }

    @Test
    public void encrypt() throws Exception {
        String encryptedStr = SecurityUtils.encrypt(getSource(), properties);
        System.out.println(encryptedStr);
    }

    @Test
    public void decrypt() throws Exception {
        String encryptedStr = SecurityUtils.encrypt(getSource(), properties);
        String decrypt = SecurityUtils.decrypt(encryptedStr, properties);
        System.out.println(decrypt);
    }

    @Test
    public void sha1() throws Exception {
        System.out.println(SecurityUtils.sha1("aaaa"));
    }

    private String getSource() {
        return "<xml><ToUserName><![CDATA[oia2TjjewbmiOUlr6X-1crbLOvLw]]>"
            + "</ToUserName><FromUserName><![CDATA[gh_7f083739789a]]></FromUserName><CreateTime>1407743423"
            + "</CreateTime><MsgType><![CDATA[video]]></MsgType><Video><MediaId><![CDATA[eYJ1MbwPRJtOvIEabaxHs7TX2D"
            + "-HV71s79GUxqdUkjm6Gs2Ed1KF3ulAOA9H1xG0]]></MediaId><Title><![CDATA[testCallBackReplyVideo]]>"
            + "</Title><Description><![CDATA[testCallBackReplyVideo]]></Description></Video></xml>";
    }

}