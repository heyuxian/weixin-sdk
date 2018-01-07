package me.javaroad.sdk.wechat.mp.handler;

import me.javaroad.sdk.wechat.mp.model.message.ResponseMessage;
import me.javaroad.sdk.wechat.mp.model.message.TextMessage;
import me.javaroad.sdk.wechat.mp.model.message.TextResponseMessage;
import org.springframework.stereotype.Component;

/**
 * @author heyx
 * 这个方法处理普通文本消息
 */
@Component
public class TextMessageHandler extends AbstractMessageHandler<TextMessage> {

    public TextMessageHandler(MessageHandlerFactory messageHandlerFactory) {
        super(messageHandlerFactory);
    }

    @Override
    public ResponseMessage handleMessage(TextMessage message) {
        // 此处根据业务对收到的消息做处理，注意，处理时间不能超过 5s，否则会导致微信对用户给出严重错误的提示
        // 如果时间很可能超过 5s, 需要在新线程中处理
        // 如果不需要对用户返回任何消息，则请在此处返回 EmptyResponseMessage
        return new TextResponseMessage();
    }

}
