package me.javaroad.sdk.wechat.mp.model.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author heyx
 */
@Getter
@Setter
public class LinkMessage extends SimpleMessage {

    @JacksonXmlProperty(localName = "Title")
    private String title;
    @JacksonXmlProperty(localName = "Description")
    private String description;
    @JacksonXmlProperty(localName = "Url")
    private String link;
    @JacksonXmlProperty(localName = "MsgType")
    private MessageType messageType = MessageType.link;
}
