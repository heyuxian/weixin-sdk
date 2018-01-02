package me.javaroad.openapi.wechat.mp.controller;

import static me.javaroad.openapi.wechat.mp.WeChatConstants.DEFAULT_ERROR_RESPONSE_MESSAGE;
import static me.javaroad.openapi.wechat.mp.WeChatConstants.DEFAULT_RESPONSE_MESSAGE;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import me.javaroad.openapi.wechat.mp.config.WeChatMpProperties;
import me.javaroad.openapi.wechat.mp.model.WeChatRequest;
import me.javaroad.openapi.wechat.mp.model.message.EmptyResponseMessage;
import me.javaroad.openapi.wechat.mp.model.message.EncryptMessage;
import me.javaroad.openapi.wechat.mp.model.message.Message;
import me.javaroad.openapi.wechat.mp.model.message.ResponseMessage;
import me.javaroad.openapi.wechat.mp.support.MessageDispatcher;
import me.javaroad.openapi.wechat.utils.MessageUtils;
import me.javaroad.openapi.wechat.utils.SecurityUtils;
import me.javaroad.openapi.wechat.utils.XmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author heyx
 */
@Slf4j
@RestController
@RequestMapping("${weixin.endpoint.callback-url:/weixin/callback}")
public class WeChatCallBackController {

    @Autowired
    private WeChatMpProperties mpProperties;
    @Autowired
    private MessageDispatcher messageDispatcher;

    @GetMapping
    public String callback(WeChatRequest weChatRequest) {
        String sign = SecurityUtils
            .sign(weChatRequest.getTimestamp(), weChatRequest.getNonce(), mpProperties.getSecurity().getToken());
        if (sign.equals(weChatRequest.getSignature())) {
            return weChatRequest.getEchostr();
        }
        return DEFAULT_ERROR_RESPONSE_MESSAGE;
    }

    @PostMapping
    public String callback(HttpServletRequest request) throws Exception {
        String requestBody = readRequestBody(request);
        Boolean isEncrypt = MessageUtils.isEncrypt(requestBody);
        String messageContentXml = requestBody;
        if (isEncrypt) {
            EncryptMessage encryptMessage = buildEncryptMessage(request, requestBody);
            messageContentXml = MessageUtils.decryptMessage(encryptMessage, mpProperties);
        }
        Message message = MessageUtils.buildMessage(messageContentXml);
        ResponseMessage responseMessage = messageDispatcher.dispatch(message);
        if (responseMessage instanceof EmptyResponseMessage) {
            return DEFAULT_RESPONSE_MESSAGE;
        }
        try {
            if (isEncrypt) {
                EncryptMessage encryptMessage = MessageUtils.encryptMessage(responseMessage, mpProperties);
                return XmlUtils.xmlString(encryptMessage);
            }
            return XmlUtils.xmlString(responseMessage);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            return DEFAULT_RESPONSE_MESSAGE;
        }
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return DEFAULT_RESPONSE_MESSAGE;
    }

    private EncryptMessage buildEncryptMessage(HttpServletRequest request, String requestBody) throws IOException {
        return EncryptMessage.builder()
            .nonce(request.getParameter("nonce"))
            .timestamp(Long.valueOf(request.getParameter("timestamp")))
            .signature(request.getParameter("msg_signature"))
            .encrypt(XmlUtils.readNode(requestBody, "Encrypt"))
            .build();
    }

    private String readRequestBody(HttpServletRequest request) throws IOException {
        BufferedReader buffer = request.getReader();
        StringBuilder builder = new StringBuilder();
        String temp;
        while ((temp = buffer.readLine()) != null) {
            builder.append(temp);
        }
        return builder.toString();
    }
}
