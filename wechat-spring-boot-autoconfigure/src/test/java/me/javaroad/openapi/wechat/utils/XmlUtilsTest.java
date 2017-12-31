package me.javaroad.openapi.wechat.utils;

import static org.assertj.core.api.Java6Assertions.assertThat;

import me.javaroad.openapi.wechat.mp.model.message.TextMessage;
import org.junit.Test;

/**
 * @author heyx
 */
public class XmlUtilsTest {

    @Test
    public void parse() throws Exception {
        String xml = getMockXml();
        TextMessage message = XmlUtils.parse(xml, TextMessage.class);
        assertThat(message.getContent()).isEqualTo("thisisatest");
    }

    private String getMockXml() {
        return "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
            + "<CreateTime>1348831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[thisisatest]]>"
            + "</Content><MsgId>1234567890123456</MsgId></xml>";
    }

    @Test
    public void readNode() throws Exception {
        String node = XmlUtils.readNode(getMockXml(), "MsgType");
        assertThat(node).isEqualTo("text");
    }

    @Test
    public void readNode_readNonExistsNode() throws Exception {
        String node = XmlUtils.readNode(getMockXml(), "UNKNOWN");
        assertThat(node).isNull();
    }
}
