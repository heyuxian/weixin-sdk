package me.javaroad.openapi.wechat.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.javaroad.openapi.wechat.exception.WeChatException;
import me.javaroad.openapi.wechat.mp.config.WeChatMpProperties;
import me.javaroad.openapi.wechat.mp.model.message.BaseMessage;
import me.javaroad.openapi.wechat.mp.model.message.BaseResponseMessage;
import me.javaroad.openapi.wechat.mp.model.message.CustomMenuEventMessage;
import me.javaroad.openapi.wechat.mp.model.message.EncryptMessage;
import me.javaroad.openapi.wechat.mp.model.message.Event;
import me.javaroad.openapi.wechat.mp.model.message.ImageMessage;
import me.javaroad.openapi.wechat.mp.model.message.LinkMessage;
import me.javaroad.openapi.wechat.mp.model.message.LocationEventMessage;
import me.javaroad.openapi.wechat.mp.model.message.LocationMessage;
import me.javaroad.openapi.wechat.mp.model.message.MessageType;
import me.javaroad.openapi.wechat.mp.model.message.QrCodeEventMessage;
import me.javaroad.openapi.wechat.mp.model.message.ShortVideoMessage;
import me.javaroad.openapi.wechat.mp.model.message.SubscribeEventMessage;
import me.javaroad.openapi.wechat.mp.model.message.TextMessage;
import me.javaroad.openapi.wechat.mp.model.message.VideoMessage;
import me.javaroad.openapi.wechat.mp.model.message.VoiceMessage;
import org.apache.commons.lang3.StringUtils;

/**
 * @author heyx
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageUtils {

    public static EncryptMessage encryptMessage(BaseResponseMessage message, WeChatMpProperties mpProperties) {
        String messageXmlStr;
        try {
            messageXmlStr = XmlUtils.xmlString(message);
        } catch (JsonProcessingException e) {
            throw new WeChatException("convert message to string failed", e);
        }
        String encryptString = SecurityUtils.encrypt(messageXmlStr, mpProperties);
        String nonce = SecurityUtils.nonceString();
        Long timestamp = System.currentTimeMillis();
        String signature = SecurityUtils.sign(encryptString, mpProperties.getSecurity().getToken(),
            nonce, timestamp);
        return EncryptMessage.builder()
            .encrypt(encryptString)
            .signature(signature)
            .timestamp(timestamp)
            .nonce(nonce)
            .build();

    }

    public static String decryptMessage(EncryptMessage encryptMessage, WeChatMpProperties mpProperties) {
        String signature = SecurityUtils.sign(encryptMessage.getEncrypt(), mpProperties.getSecurity().getToken(),
            encryptMessage.getNonce(), encryptMessage.getTimestamp());
        if (!Objects.equals(encryptMessage.getSignature(), signature)) {
            throw new WeChatException("invalid message signature");
        }
        return SecurityUtils.decrypt(encryptMessage.getEncrypt(), mpProperties);
    }

    public static BaseMessage buildMessage(String xmlMessageContent) {
        String msgType;
        try {
            msgType = XmlUtils.readNode(xmlMessageContent, "MsgType");
        } catch (IOException e) {
            throw new WeChatException("read message type failed, xml=" + xmlMessageContent, e);
        }
        if (StringUtils.isBlank(msgType)) {
            throw new IllegalArgumentException("invalid request param, xml=[" + xmlMessageContent + "]");
        }
        MessageType messageType = MessageType.valueOf(msgType);
        try {
            return buildMessage(messageType, xmlMessageContent);
        } catch (IOException e) {
            throw new WeChatException("build message failed", e);
        }
    }

    private static BaseMessage buildMessage(MessageType messageType, String xmlStr) throws IOException {
        switch (messageType) {
            case text:
                return XmlUtils.parse(xmlStr, TextMessage.class);
            case link:
                return XmlUtils.parse(xmlStr, LinkMessage.class);
            case video:
                return XmlUtils.parse(xmlStr, VideoMessage.class);
            case shortvideo:
                return XmlUtils.parse(xmlStr, ShortVideoMessage.class);
            case voice:
                return XmlUtils.parse(xmlStr, VoiceMessage.class);
            case location:
                return XmlUtils.parse(xmlStr, LocationMessage.class);
            case image:
                return XmlUtils.parse(xmlStr, ImageMessage.class);
            case event:
                return buildEventMessage(xmlStr);
            default:
                return null;
        }
    }

    private static BaseMessage buildEventMessage(String xmlStr) throws IOException {
        String eventStr = XmlUtils.readNode(xmlStr, "Event");
        if (StringUtils.isBlank(eventStr)) {
            throw new IllegalArgumentException("invalid request param, xml=[" + xmlStr + "]");
        }
        Event event = Event.valueOf(eventStr);
        switch (event) {
            case subscribe:
                String ticket = XmlUtils.readNode(xmlStr, "Ticket");
                if (StringUtils.isBlank(ticket)) {
                    return XmlUtils.parse(xmlStr, SubscribeEventMessage.class);
                }
                return XmlUtils.parse(xmlStr, QrCodeEventMessage.class);
            case unsubscribe:
                return XmlUtils.parse(xmlStr, SubscribeEventMessage.class);
            case SCAN:
                return XmlUtils.parse(xmlStr, QrCodeEventMessage.class);
            case LOCATION:
                return XmlUtils.parse(xmlStr, LocationEventMessage.class);
            case CLICK:
            case VIEW:
                return XmlUtils.parse(xmlStr, CustomMenuEventMessage.class);
            default:
                return null;
        }
    }

    public static boolean isEncrypt(String xmlStr) {
        String encrypt;
        try {
            encrypt = XmlUtils.readNode(xmlStr, "Encrypt");
        } catch (IOException e) {
            throw new WeChatException(e.getMessage(), e);
        }
        return StringUtils.isNotBlank(encrypt);
    }
}
