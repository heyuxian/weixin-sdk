package me.javaroad.openapi.wechat.mp.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import me.javaroad.openapi.wechat.mp.model.message.BaseMessage;
import me.javaroad.openapi.wechat.mp.model.message.CustomMenuEventMessage;
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
import me.javaroad.openapi.wechat.utils.XmlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author heyx
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new WeChatMessageResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    class WeChatMessageResolver implements HandlerMethodArgumentResolver {

        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return BaseMessage.class.isAssignableFrom(parameter.getParameterType());
        }

        @Override
        public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

            BufferedReader buffer = webRequest.getNativeRequest(HttpServletRequest.class).getReader();

            StringBuilder builder = new StringBuilder();
            String temp;
            while ((temp = buffer.readLine()) != null) {
                builder.append(temp);
            }
            String xmlStr = builder.toString();
            String msgType = XmlUtils.readNode(xmlStr, "MsgType");
            if (StringUtils.isBlank(msgType)) {
                throw new IllegalArgumentException("invalid request param, xml=[" + xmlStr + "]");
            }
            MessageType messageType = MessageType.valueOf(msgType);
            return buildMessage(messageType, xmlStr);
        }

        private BaseMessage buildMessage(MessageType messageType, String xmlStr) throws IOException {
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

        private BaseMessage buildEventMessage(String xmlStr) throws IOException {
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
    }


}
