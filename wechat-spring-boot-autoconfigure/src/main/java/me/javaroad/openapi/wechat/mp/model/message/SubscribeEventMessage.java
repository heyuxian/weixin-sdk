package me.javaroad.openapi.wechat.mp.model.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author heyx
 */
@Getter
@Setter
public class SubscribeEventMessage extends EventMessage {
    @JacksonXmlProperty(localName = "Event")
    private Event event;
}
