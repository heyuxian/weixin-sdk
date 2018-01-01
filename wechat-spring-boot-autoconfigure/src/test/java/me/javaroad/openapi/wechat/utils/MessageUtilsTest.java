package me.javaroad.openapi.wechat.utils;

import static org.assertj.core.api.Java6Assertions.assertThat;

import me.javaroad.openapi.wechat.BaseSpringTest;
import me.javaroad.openapi.wechat.mp.config.WeChatMpProperties;
import me.javaroad.openapi.wechat.mp.model.message.EncryptMessage;
import me.javaroad.openapi.wechat.mp.model.message.TextResponseMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author heyx
 */
public class MessageUtilsTest extends BaseSpringTest {

    @Autowired
    private WeChatMpProperties mpProperties;

    @Test
    public void encryptMessage() throws Exception {
        TextResponseMessage textResponseMessage = new TextResponseMessage();
        textResponseMessage.setContent("测试消息");
        EncryptMessage encryptMessage = MessageUtils.encryptMessage(textResponseMessage, mpProperties);
        assertThat(encryptMessage).isNotNull();
    }

    /**
     * todo
     */
    @Test
    public void decryptMessage() throws Exception {
    }

}