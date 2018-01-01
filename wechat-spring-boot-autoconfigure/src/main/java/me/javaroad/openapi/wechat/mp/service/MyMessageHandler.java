package me.javaroad.openapi.wechat.mp.service;

import java.util.Date;
import me.javaroad.openapi.wechat.mp.model.message.BaseMessage;
import me.javaroad.openapi.wechat.mp.model.message.BaseResponseMessage;
import me.javaroad.openapi.wechat.mp.model.message.TextResponseMessage;
import org.springframework.stereotype.Service;

/**
 * @author heyx
 */
@Service
public class MyMessageHandler implements MessageHandler {

    @Override
    public BaseResponseMessage handleMessage(BaseMessage message) {
        TextResponseMessage responseMessage = new TextResponseMessage();
        responseMessage.setFrom(message.getTo());
        responseMessage.setTo(message.getFrom());
        responseMessage.setCreateTime(new Date().getTime());
        responseMessage.setContent("测试");
        return responseMessage;
    }
}
