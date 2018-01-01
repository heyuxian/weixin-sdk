package me.javaroad.openapi.wechat.mp.controller;

import static me.javaroad.openapi.wechat.mp.WeChatConstants.DEFAULT_RESPONSE_MESSAGE;
import static me.javaroad.openapi.wechat.mp.WeChatConstants.ENCRYPT_MESSAGE_PARAM;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.javaroad.openapi.wechat.mp.config.WeChatMpProperties;
import me.javaroad.openapi.wechat.mp.event.ReceiveMessageEvent;
import me.javaroad.openapi.wechat.mp.model.WeChatRequest;
import me.javaroad.openapi.wechat.mp.model.message.BaseMessage;
import me.javaroad.openapi.wechat.mp.model.message.BaseResponseMessage;
import me.javaroad.openapi.wechat.mp.model.message.EmptyResponseMessage;
import me.javaroad.openapi.wechat.mp.model.message.EncryptMessage;
import me.javaroad.openapi.wechat.mp.service.MessageHandler;
import me.javaroad.openapi.wechat.utils.MessageUtils;
import me.javaroad.openapi.wechat.utils.SecurityUtils;
import me.javaroad.openapi.wechat.utils.XmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author heyx
 */
@Slf4j
@RestController
@RequestMapping("wechat")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class WeChatCallBackController {

    private final WeChatMpProperties mpProperties;
    private final ApplicationContext applicationContext;
    private final MessageHandler handler;

    @GetMapping("callback")
    public String callback(WeChatRequest weChatRequest) {
        String sign = SecurityUtils
            .sign(weChatRequest.getTimestamp(), weChatRequest.getNonce(), mpProperties.getSecurity().getToken());
        if (sign.equals(weChatRequest.getSignature())) {
            return weChatRequest.getEchostr();
        }
        return "error";
    }

    @PostMapping("callback")
    public String callback(BaseMessage message, HttpServletRequest request) {
        applicationContext.publishEvent(new ReceiveMessageEvent(message));
        BaseResponseMessage responseMessage = handler.handleMessage(message);
        try {
            if (responseMessage instanceof EmptyResponseMessage) {
                return DEFAULT_RESPONSE_MESSAGE;
            }
            Object isEncryptMessage = request.getAttribute(ENCRYPT_MESSAGE_PARAM);
            if (Objects.nonNull(isEncryptMessage)) {
                EncryptMessage encryptMessage = MessageUtils.encryptMessage(responseMessage, mpProperties);
                return XmlUtils.xmlString(encryptMessage);
            }
            return XmlUtils.xmlString(responseMessage);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return DEFAULT_RESPONSE_MESSAGE;
        }
    }
}
