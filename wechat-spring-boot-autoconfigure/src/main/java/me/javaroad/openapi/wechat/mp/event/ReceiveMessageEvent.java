package me.javaroad.openapi.wechat.mp.event;

import me.javaroad.openapi.wechat.mp.model.message.BaseMessage;
import org.springframework.context.ApplicationEvent;

/**
 * @author heyx
 */
public class ReceiveMessageEvent extends ApplicationEvent {

    public ReceiveMessageEvent(BaseMessage source) {
        super(source);
    }
}
