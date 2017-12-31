package me.javaroad.openapi.wechat.mp.controller;

import me.javaroad.openapi.wechat.mp.config.WeChatMpProperties;
import me.javaroad.openapi.wechat.mp.event.ReceiveMessageEvent;
import me.javaroad.openapi.wechat.mp.model.message.BaseMessage;
import me.javaroad.openapi.wechat.mp.model.response.WeChatRequest;
import me.javaroad.openapi.wechat.utils.SignUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author heyx
 */
@RestController
@RequestMapping("wechat")
public class WeChatCallBackController {

    @Autowired
    private WeChatMpProperties weChatMpProperties;
    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("callback")
    public String callback(WeChatRequest weChatRequest) {
        String sign = SignUtils
            .sign(weChatRequest.getTimestamp(), weChatRequest.getNonce(), weChatMpProperties.getClient().getToken());
        if (sign.equals(weChatRequest.getSignature())) {
            return weChatRequest.getEchostr();
        }
        return "error";
    }

    @PostMapping("callback")
    public String callback(BaseMessage message) {
        applicationContext.publishEvent(new ReceiveMessageEvent(message));
        return "";
    }
}
