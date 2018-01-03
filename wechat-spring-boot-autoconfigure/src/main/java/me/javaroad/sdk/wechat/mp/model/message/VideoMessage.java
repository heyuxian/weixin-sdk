package me.javaroad.sdk.wechat.mp.model.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author heyx
 */
@Getter
@Setter
public class VideoMessage extends SimpleMessage {
    @JacksonXmlProperty(localName = "MediaId")
    private String mediaId;
    @JacksonXmlProperty(localName = "ThumbMediaId")
    private String thumbMediaId;
    @JacksonXmlProperty(localName = "MsgType")
    private MessageType messageType = MessageType.video;
}
