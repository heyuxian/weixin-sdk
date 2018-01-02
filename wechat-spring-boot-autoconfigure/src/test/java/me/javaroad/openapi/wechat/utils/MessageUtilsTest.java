package me.javaroad.openapi.wechat.utils;

import static org.assertj.core.api.Java6Assertions.assertThat;

import me.javaroad.openapi.wechat.BaseSpringTest;
import me.javaroad.openapi.wechat.mp.config.WeChatMpProperties;
import me.javaroad.openapi.wechat.mp.model.message.AbstractMessage;
import me.javaroad.openapi.wechat.mp.model.message.CustomMenuEventMessage;
import me.javaroad.openapi.wechat.mp.model.message.EncryptMessage;
import me.javaroad.openapi.wechat.mp.model.message.Event;
import me.javaroad.openapi.wechat.mp.model.message.ImageMessage;
import me.javaroad.openapi.wechat.mp.model.message.LinkMessage;
import me.javaroad.openapi.wechat.mp.model.message.LocationEventMessage;
import me.javaroad.openapi.wechat.mp.model.message.LocationMessage;
import me.javaroad.openapi.wechat.mp.model.message.Message;
import me.javaroad.openapi.wechat.mp.model.message.QrCodeEventMessage;
import me.javaroad.openapi.wechat.mp.model.message.ShortVideoMessage;
import me.javaroad.openapi.wechat.mp.model.message.SubscribeEventMessage;
import me.javaroad.openapi.wechat.mp.model.message.TextMessage;
import me.javaroad.openapi.wechat.mp.model.message.TextResponseMessage;
import me.javaroad.openapi.wechat.mp.model.message.VideoMessage;
import me.javaroad.openapi.wechat.mp.model.message.VoiceMessage;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
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

    @Test
    public void buildMessage_textMessage() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
                + "<CreateTime>1348831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content>"
                + "<![CDATA[thisisatest]]></Content><MsgId>1234567890123456</MsgId></xml>";
        buildMessage(messageXml, new ArgumentMatcher<TextMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!TextMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                TextMessage message = (TextMessage) param;
                Assertions.assertThat(message.getMessageId()).isEqualTo(1234567890123456L);
                Assertions.assertThat(message.getContent()).isEqualTo("thisisatest");
                return true;
            }
        });
    }

    @Test
    public void buildMessage_imageMessage() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
                + "<CreateTime>1348831860</CreateTime><MsgType><![CDATA[image]]></MsgType><PicUrl>"
                + "<![CDATA[thisisaurl]]></PicUrl><MediaId><![CDATA[media_id]]></MediaId><MsgId>1234567890123456"
                + "</MsgId></xml>";
        buildMessage(messageXml, new ArgumentMatcher<ImageMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!ImageMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                ImageMessage message = (ImageMessage) param;
                Assertions.assertThat(message.getMessageId()).isEqualTo(1234567890123456L);
                Assertions.assertThat(message.getPicUrl()).isEqualTo("thisisaurl");
                return true;
            }
        });
    }

    @Test
    public void buildMessage_voiceMessage() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
                + "<CreateTime>1357290913</CreateTime><MsgType><![CDATA[voice]]></MsgType><MediaId>"
                + "<![CDATA[media_id]]></MediaId><Format><![CDATA[Format]]></Format><MsgId>1234567890123456"
                + "</MsgId></xml>";
        buildMessage(messageXml, new ArgumentMatcher<VoiceMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!VoiceMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                VoiceMessage message = (VoiceMessage) param;
                Assertions.assertThat(message.getMessageId()).isEqualTo(1234567890123456L);
                Assertions.assertThat(message.getMediaId()).isEqualTo("media_id");
                Assertions.assertThat(message.getFormat()).isEqualTo("Format");
                return true;
            }
        });
    }

    @Test
    public void buildMessage_voiceMessageRecognition() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
                + "<CreateTime>1357290913</CreateTime><MsgType><![CDATA[voice]]></MsgType><MediaId>"
                + "<![CDATA[media_id]]></MediaId><Format><![CDATA[Format]]></Format><Recognition>"
                + "<![CDATA[腾讯微信团队]]></Recognition><MsgId>1234567890123456</MsgId></xml>";
        buildMessage(messageXml, new ArgumentMatcher<VoiceMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!VoiceMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                VoiceMessage message = (VoiceMessage) param;
                Assertions.assertThat(message.getMessageId()).isEqualTo(1234567890123456L);
                Assertions.assertThat(message.getMediaId()).isEqualTo("media_id");
                Assertions.assertThat(message.getFormat()).isEqualTo("Format");
                Assertions.assertThat(message.getRecognition()).isEqualTo("腾讯微信团队");
                return true;
            }
        });
    }

    @Test
    public void buildMessage_videoMessage() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
                + "<CreateTime>1357290913</CreateTime><MsgType><![CDATA[video]]></MsgType><MediaId>"
                + "<![CDATA[media_id]]></MediaId><ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>"
                + "<MsgId>1234567890123456</MsgId></xml>";
        buildMessage(messageXml, new ArgumentMatcher<VideoMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!VideoMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                VideoMessage message = (VideoMessage) param;
                Assertions.assertThat(message.getMessageId()).isEqualTo(1234567890123456L);
                Assertions.assertThat(message.getMediaId()).isEqualTo("media_id");
                Assertions.assertThat(message.getThumbMediaId()).isEqualTo("thumb_media_id");
                return true;
            }
        });
    }

    @Test
    public void buildMessage_shortVideoMessage() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
                + "<CreateTime>1357290913</CreateTime><MsgType><![CDATA[shortvideo]]></MsgType><MediaId>"
                + "<![CDATA[media_id]]></MediaId><ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>"
                + "<MsgId>1234567890123456</MsgId></xml>";
        buildMessage(messageXml, new ArgumentMatcher<ShortVideoMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!ShortVideoMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                ShortVideoMessage message = (ShortVideoMessage) param;
                Assertions.assertThat(message.getMessageId()).isEqualTo(1234567890123456L);
                Assertions.assertThat(message.getMediaId()).isEqualTo("media_id");
                Assertions.assertThat(message.getThumbMediaId()).isEqualTo("thumb_media_id");
                return true;
            }
        });
    }

    @Test
    public void buildMessage_locationMessage() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
                + "<CreateTime>1351776360</CreateTime><MsgType><![CDATA[location]]></MsgType>"
                + "<Location_X>23.134521</Location_X><Location_Y>113.358803</Location_Y>"
                + "<Scale>20</Scale><Label><![CDATA[位置信息]]></Label><MsgId>1234567890123456</MsgId></xml>";
        buildMessage(messageXml, new ArgumentMatcher<LocationMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!LocationMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                LocationMessage message = (LocationMessage) param;
                Assertions.assertThat(message.getMessageId()).isEqualTo(1234567890123456L);
                Assertions.assertThat(message.getLocationX()).isEqualTo("23.134521");
                Assertions.assertThat(message.getLocationY()).isEqualTo("113.358803");
                Assertions.assertThat(message.getScale()).isEqualTo(20);
                Assertions.assertThat(message.getLabel()).isEqualTo("位置信息");
                return true;
            }
        });
    }

    @Test
    public void buildMessage_linkMessage() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
                + "<CreateTime>1351776360</CreateTime><MsgType><![CDATA[link]]></MsgType><Title>"
                + "<![CDATA[公众平台官网链接]]></Title><Description><![CDATA[公众平台官网链接]]></Description><Url>"
                + "<![CDATA[url]]></Url><MsgId>1234567890123456</MsgId></xml>";
        buildMessage(messageXml, new ArgumentMatcher<LinkMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!LinkMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                LinkMessage message = (LinkMessage) param;
                Assertions.assertThat(message.getMessageId()).isEqualTo(1234567890123456L);
                Assertions.assertThat(message.getTitle()).isEqualTo("公众平台官网链接");
                Assertions.assertThat(message.getDescription()).isEqualTo("公众平台官网链接");
                Assertions.assertThat(message.getLink()).isEqualTo("url");
                return true;
            }
        });
    }

    @Test
    public void buildMessage_subscribeEventMessage() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[FromUser]]></FromUserName>"
                + "<CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[subscribe]]>"
                + "</Event></xml>";
        buildMessage(messageXml, new ArgumentMatcher<SubscribeEventMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!SubscribeEventMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                SubscribeEventMessage message = (SubscribeEventMessage) param;
                Assertions.assertThat(message.getEvent()).isEqualTo(Event.subscribe);
                return true;
            }
        });
    }

    @Test
    public void buildMessage_unsubscribeEventMessage() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[FromUser]]></FromUserName>"
                + "<CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[unsubscribe]]>"
                + "</Event></xml>";
        buildMessage(messageXml, new ArgumentMatcher<SubscribeEventMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!SubscribeEventMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                SubscribeEventMessage message = (SubscribeEventMessage) param;
                Assertions.assertThat(message.getEvent()).isEqualTo(Event.unsubscribe);
                return true;
            }
        });
    }

    @Test
    public void buildMessage_qrCodeEventMessage_subscribe() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[FromUser]]></FromUserName>"
                + "<CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[subscribe]]>"
                + "</Event><EventKey><![CDATA[qrscene_123123]]></EventKey><Ticket><![CDATA[TICKET]]></Ticket></xml>";
        buildMessage(messageXml, new ArgumentMatcher<QrCodeEventMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!QrCodeEventMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                QrCodeEventMessage message = (QrCodeEventMessage) param;
                Assertions.assertThat(message.getEvent()).isEqualTo(Event.subscribe);
                Assertions.assertThat(message.getEventKey()).isEqualTo("qrscene_123123");
                Assertions.assertThat(message.getTicket()).isEqualTo("TICKET");
                return true;
            }
        });
    }

    @Test
    public void buildMessage_qrCodeEventMessage_scan() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[FromUser]]></FromUserName>"
                + "<CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[SCAN]]>"
                + "</Event><EventKey><![CDATA[SCENE_VALUE]]></EventKey><Ticket><![CDATA[TICKET]]></Ticket></xml>";
        buildMessage(messageXml, new ArgumentMatcher<QrCodeEventMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!QrCodeEventMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                QrCodeEventMessage message = (QrCodeEventMessage) param;
                Assertions.assertThat(message.getEvent()).isEqualTo(Event.SCAN);
                Assertions.assertThat(message.getEventKey()).isEqualTo("SCENE_VALUE");
                Assertions.assertThat(message.getTicket()).isEqualTo("TICKET");
                return true;
            }
        });
    }

    @Test
    public void buildMessage_locationEventMessage() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]>"
                + "</FromUserName><CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]>"
                + "</MsgType><Event><![CDATA[LOCATION]]></Event><Latitude>23.137466</Latitude>"
                + "<Longitude>113.352425</Longitude><Precision>119.385040</Precision></xml>";
        buildMessage(messageXml, new ArgumentMatcher<LocationEventMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!LocationEventMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                LocationEventMessage message = (LocationEventMessage) param;
                Assertions.assertThat(message.getEvent()).isEqualTo(Event.LOCATION);
                Assertions.assertThat(message.getLatitude()).isEqualTo("23.137466");
                Assertions.assertThat(message.getLongitude()).isEqualTo("113.352425");
                Assertions.assertThat(message.getPrecision()).isEqualTo("119.385040");
                return true;
            }
        });
    }

    @Test
    public void buildMessage_customMenuEventMessage_click() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[FromUser]]></FromUserName>"
                + "<CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[CLICK]]>"
                + "</Event><EventKey><![CDATA[EVENTKEY]]></EventKey></xml>";
        buildMessage(messageXml, new ArgumentMatcher<CustomMenuEventMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!CustomMenuEventMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                CustomMenuEventMessage message = (CustomMenuEventMessage) param;
                Assertions.assertThat(message.getEvent()).isEqualTo(Event.CLICK);
                Assertions.assertThat(message.getEventKey()).isEqualTo("EVENTKEY");
                return true;
            }
        });
    }

    @Test
    public void buildMessage_customMenuEventMessage_view() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[FromUser]]></FromUserName>"
                + "<CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[VIEW]]>"
                + "</Event><EventKey><![CDATA[www.qq.com]]></EventKey></xml>";
        buildMessage(messageXml, new ArgumentMatcher<CustomMenuEventMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!CustomMenuEventMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                CustomMenuEventMessage message = (CustomMenuEventMessage) param;
                Assertions.assertThat(message.getEvent()).isEqualTo(Event.VIEW);
                Assertions.assertThat(message.getEventKey()).isEqualTo("www.qq.com");
                return true;
            }
        });
    }

    private void buildMessage(String messageXml, Matcher<? extends AbstractMessage> matcher) {
        Message message = MessageUtils.buildMessage(messageXml);
        matcher.matches(message);

    }


}