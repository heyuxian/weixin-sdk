package me.javaroad.openapi.wechat.mp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;

import me.javaroad.openapi.wechat.BaseSpringMvcTest;
import me.javaroad.openapi.wechat.mp.model.message.BaseMessage;
import me.javaroad.openapi.wechat.mp.model.message.CustomMenuEventMessage;
import me.javaroad.openapi.wechat.mp.model.message.Event;
import me.javaroad.openapi.wechat.mp.model.message.ImageMessage;
import me.javaroad.openapi.wechat.mp.model.message.LinkMessage;
import me.javaroad.openapi.wechat.mp.model.message.LocationEventMessage;
import me.javaroad.openapi.wechat.mp.model.message.LocationMessage;
import me.javaroad.openapi.wechat.mp.model.message.QrCodeEventMessage;
import me.javaroad.openapi.wechat.mp.model.message.ShortVideoMessage;
import me.javaroad.openapi.wechat.mp.model.message.SubscribeEventMessage;
import me.javaroad.openapi.wechat.mp.model.message.TextMessage;
import me.javaroad.openapi.wechat.mp.model.message.VideoMessage;
import me.javaroad.openapi.wechat.mp.model.message.VoiceMessage;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author heyx
 */
public class WeChatCallBackControllerTest extends BaseSpringMvcTest {

    @MockBean
    private WeChatCallBackController callBackController;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void callback_textMessage() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
                + "<CreateTime>1348831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content>"
                + "<![CDATA[thisisatest]]></Content><MsgId>1234567890123456</MsgId></xml>";
        callback(messageXml, new ArgumentMatcher<TextMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!TextMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                TextMessage message = (TextMessage) param;
                assertThat(message.getMessageId()).isEqualTo(1234567890123456L);
                assertThat(message.getContent()).isEqualTo("thisisatest");
                return true;
            }
        });
    }

    @Test
    public void callback_imageMessage() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
                + "<CreateTime>1348831860</CreateTime><MsgType><![CDATA[image]]></MsgType><PicUrl>"
                + "<![CDATA[thisisaurl]]></PicUrl><MediaId><![CDATA[media_id]]></MediaId><MsgId>1234567890123456"
                + "</MsgId></xml>";
        callback(messageXml, new ArgumentMatcher<ImageMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!ImageMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                ImageMessage message = (ImageMessage) param;
                assertThat(message.getMessageId()).isEqualTo(1234567890123456L);
                assertThat(message.getPicUrl()).isEqualTo("thisisaurl");
                return true;
            }
        });
    }

    @Test
    public void callback_voiceMessage() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
                + "<CreateTime>1357290913</CreateTime><MsgType><![CDATA[voice]]></MsgType><MediaId>"
                + "<![CDATA[media_id]]></MediaId><Format><![CDATA[Format]]></Format><MsgId>1234567890123456"
                + "</MsgId></xml>";
        callback(messageXml, new ArgumentMatcher<VoiceMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!VoiceMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                VoiceMessage message = (VoiceMessage) param;
                assertThat(message.getMessageId()).isEqualTo(1234567890123456L);
                assertThat(message.getMediaId()).isEqualTo("media_id");
                assertThat(message.getFormat()).isEqualTo("Format");
                return true;
            }
        });
    }

    @Test
    public void callback_voiceMessageRecognition() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
                + "<CreateTime>1357290913</CreateTime><MsgType><![CDATA[voice]]></MsgType><MediaId>"
                + "<![CDATA[media_id]]></MediaId><Format><![CDATA[Format]]></Format><Recognition>"
                + "<![CDATA[腾讯微信团队]]></Recognition><MsgId>1234567890123456</MsgId></xml>";
        callback(messageXml, new ArgumentMatcher<VoiceMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!VoiceMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                VoiceMessage message = (VoiceMessage) param;
                assertThat(message.getMessageId()).isEqualTo(1234567890123456L);
                assertThat(message.getMediaId()).isEqualTo("media_id");
                assertThat(message.getFormat()).isEqualTo("Format");
                assertThat(message.getRecognition()).isEqualTo("腾讯微信团队");
                return true;
            }
        });
    }

    @Test
    public void callback_videoMessage() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
                + "<CreateTime>1357290913</CreateTime><MsgType><![CDATA[video]]></MsgType><MediaId>"
                + "<![CDATA[media_id]]></MediaId><ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>"
                + "<MsgId>1234567890123456</MsgId></xml>";
        callback(messageXml, new ArgumentMatcher<VideoMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!VideoMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                VideoMessage message = (VideoMessage) param;
                assertThat(message.getMessageId()).isEqualTo(1234567890123456L);
                assertThat(message.getMediaId()).isEqualTo("media_id");
                assertThat(message.getThumbMediaId()).isEqualTo("thumb_media_id");
                return true;
            }
        });
    }

    @Test
    public void callback_shortVideoMessage() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
                + "<CreateTime>1357290913</CreateTime><MsgType><![CDATA[shortvideo]]></MsgType><MediaId>"
                + "<![CDATA[media_id]]></MediaId><ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>"
                + "<MsgId>1234567890123456</MsgId></xml>";
        callback(messageXml, new ArgumentMatcher<ShortVideoMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!ShortVideoMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                ShortVideoMessage message = (ShortVideoMessage) param;
                assertThat(message.getMessageId()).isEqualTo(1234567890123456L);
                assertThat(message.getMediaId()).isEqualTo("media_id");
                assertThat(message.getThumbMediaId()).isEqualTo("thumb_media_id");
                return true;
            }
        });
    }

    @Test
    public void callback_locationMessage() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
                + "<CreateTime>1351776360</CreateTime><MsgType><![CDATA[location]]></MsgType>"
                + "<Location_X>23.134521</Location_X><Location_Y>113.358803</Location_Y>"
                + "<Scale>20</Scale><Label><![CDATA[位置信息]]></Label><MsgId>1234567890123456</MsgId></xml>";
        callback(messageXml, new ArgumentMatcher<LocationMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!LocationMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                LocationMessage message = (LocationMessage) param;
                assertThat(message.getMessageId()).isEqualTo(1234567890123456L);
                assertThat(message.getLocationX()).isEqualTo("23.134521");
                assertThat(message.getLocationY()).isEqualTo("113.358803");
                assertThat(message.getScale()).isEqualTo(20);
                assertThat(message.getLabel()).isEqualTo("位置信息");
                return true;
            }
        });
    }

    @Test
    public void callback_linkMessage() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
                + "<CreateTime>1351776360</CreateTime><MsgType><![CDATA[link]]></MsgType><Title>"
                + "<![CDATA[公众平台官网链接]]></Title><Description><![CDATA[公众平台官网链接]]></Description><Url>"
                + "<![CDATA[url]]></Url><MsgId>1234567890123456</MsgId></xml>";
        callback(messageXml, new ArgumentMatcher<LinkMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!LinkMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                LinkMessage message = (LinkMessage) param;
                assertThat(message.getMessageId()).isEqualTo(1234567890123456L);
                assertThat(message.getTitle()).isEqualTo("公众平台官网链接");
                assertThat(message.getDescription()).isEqualTo("公众平台官网链接");
                assertThat(message.getUrl()).isEqualTo("url");
                return true;
            }
        });
    }

    @Test
    public void callback_subscribeEventMessage() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[FromUser]]></FromUserName>"
                + "<CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[subscribe]]>"
                + "</Event></xml>";
        callback(messageXml, new ArgumentMatcher<SubscribeEventMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!SubscribeEventMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                SubscribeEventMessage message = (SubscribeEventMessage) param;
                assertThat(message.getEvent()).isEqualTo(Event.subscribe);
                return true;
            }
        });
    }

    @Test
    public void callback_unsubscribeEventMessage() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[FromUser]]></FromUserName>"
                + "<CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[unsubscribe]]>"
                + "</Event></xml>";
        callback(messageXml, new ArgumentMatcher<SubscribeEventMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!SubscribeEventMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                SubscribeEventMessage message = (SubscribeEventMessage) param;
                assertThat(message.getEvent()).isEqualTo(Event.unsubscribe);
                return true;
            }
        });
    }

    @Test
    public void callback_qrCodeEventMessage_subscribe() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[FromUser]]></FromUserName>"
                + "<CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[subscribe]]>"
                + "</Event><EventKey><![CDATA[qrscene_123123]]></EventKey><Ticket><![CDATA[TICKET]]></Ticket></xml>";
        callback(messageXml, new ArgumentMatcher<QrCodeEventMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!QrCodeEventMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                QrCodeEventMessage message = (QrCodeEventMessage) param;
                assertThat(message.getEvent()).isEqualTo(Event.subscribe);
                assertThat(message.getEventKey()).isEqualTo("qrscene_123123");
                assertThat(message.getTicket()).isEqualTo("TICKET");
                return true;
            }
        });
    }

    @Test
    public void callback_qrCodeEventMessage_scan() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[FromUser]]></FromUserName>"
                + "<CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[SCAN]]>"
                + "</Event><EventKey><![CDATA[SCENE_VALUE]]></EventKey><Ticket><![CDATA[TICKET]]></Ticket></xml>";
        callback(messageXml, new ArgumentMatcher<QrCodeEventMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!QrCodeEventMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                QrCodeEventMessage message = (QrCodeEventMessage) param;
                assertThat(message.getEvent()).isEqualTo(Event.SCAN);
                assertThat(message.getEventKey()).isEqualTo("SCENE_VALUE");
                assertThat(message.getTicket()).isEqualTo("TICKET");
                return true;
            }
        });
    }

    @Test
    public void callback_locationEventMessage() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]>"
                + "</FromUserName><CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]>"
                + "</MsgType><Event><![CDATA[LOCATION]]></Event><Latitude>23.137466</Latitude>"
                + "<Longitude>113.352425</Longitude><Precision>119.385040</Precision></xml>";
        callback(messageXml, new ArgumentMatcher<LocationEventMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!LocationEventMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                LocationEventMessage message = (LocationEventMessage) param;
                assertThat(message.getEvent()).isEqualTo(Event.LOCATION);
                assertThat(message.getLatitude()).isEqualTo("23.137466");
                assertThat(message.getLongitude()).isEqualTo("113.352425");
                assertThat(message.getPrecision()).isEqualTo("119.385040");
                return true;
            }
        });
    }

    @Test
    public void callback_customMenuEventMessage_click() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[FromUser]]></FromUserName>"
                + "<CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[CLICK]]>"
                + "</Event><EventKey><![CDATA[EVENTKEY]]></EventKey></xml>";
        callback(messageXml, new ArgumentMatcher<CustomMenuEventMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!CustomMenuEventMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                CustomMenuEventMessage message = (CustomMenuEventMessage) param;
                assertThat(message.getEvent()).isEqualTo(Event.CLICK);
                assertThat(message.getEventKey()).isEqualTo("EVENTKEY");
                return true;
            }
        });
    }

    @Test
    public void callback_customMenuEventMessage_view() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[FromUser]]></FromUserName>"
                + "<CreateTime>123456789</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[VIEW]]>"
                + "</Event><EventKey><![CDATA[www.qq.com]]></EventKey></xml>";
        callback(messageXml, new ArgumentMatcher<CustomMenuEventMessage>() {
            @Override
            public boolean matches(Object param) {
                if (!CustomMenuEventMessage.class.isAssignableFrom(param.getClass())) {
                    return false;
                }
                CustomMenuEventMessage message = (CustomMenuEventMessage) param;
                assertThat(message.getEvent()).isEqualTo(Event.VIEW);
                assertThat(message.getEventKey()).isEqualTo("www.qq.com");
                return true;
            }
        });
    }

    private void callback(String messageXml, Matcher<? extends BaseMessage> matcher) {
        ResponseEntity<String> responseEntity = restTemplate
            .postForEntity("/wechat/callback", messageXml, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotEqualTo("error");
        verify(callBackController).callback(argThat(matcher));
    }

}