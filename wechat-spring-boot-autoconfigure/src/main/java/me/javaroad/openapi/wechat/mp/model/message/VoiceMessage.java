package me.javaroad.openapi.wechat.mp.model.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author heyx
 */
@Getter
@Setter
public class VoiceMessage extends SimpleMessage {
    @JacksonXmlProperty(localName = "MediaId")
    private String mediaId;
    @JacksonXmlProperty(localName = "Format")
    private String format;
    @JacksonXmlProperty(localName = "Recognition")
    private String recognition;
    @JacksonXmlProperty(localName = "MsgType")
    private MessageType messageType = MessageType.voice;
}
