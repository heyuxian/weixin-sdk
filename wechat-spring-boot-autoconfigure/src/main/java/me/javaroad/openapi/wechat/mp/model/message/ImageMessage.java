package me.javaroad.openapi.wechat.mp.model.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author heyx
 */
@Getter
@Setter
public class ImageMessage extends SimpleMessage {
    @JacksonXmlProperty(localName = "MediaId")
    private String mediaId;
    @JacksonXmlProperty(localName = "PicUrl")
    private String picUrl;
    @JacksonXmlProperty(localName = "MsgType")
    private MessageType messageType = MessageType.image;
}
