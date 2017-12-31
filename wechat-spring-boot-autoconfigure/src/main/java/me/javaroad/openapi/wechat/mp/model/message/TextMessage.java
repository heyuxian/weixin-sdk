package me.javaroad.openapi.wechat.mp.model.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author heyx
 */
@Getter
@Setter
public class TextMessage extends SimpleMessage {
    @JacksonXmlProperty(localName = "Content")
    private String content;
    @JacksonXmlProperty(localName = "MsgType")
    private MessageType messageType = MessageType.text;
}
