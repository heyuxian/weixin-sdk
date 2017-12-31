package me.javaroad.openapi.wechat.mp.model.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author heyx
 */
@Getter
@Setter
public abstract class EventMessage extends BaseMessage {

    @JacksonXmlProperty(localName = "MsgType")
    private MessageType messageType = MessageType.event;

    public abstract Event getEvent();
}
