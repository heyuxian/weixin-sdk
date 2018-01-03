package me.javaroad.sdk.wechat.mp.model.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author heyx
 */
@Getter
@Setter
public class MusicResponseMessage extends AbstractResponseMessage {
    @JacksonXmlProperty(localName = "MediaId")
    private String mediaId;
    @JacksonXmlProperty(localName = "Title")
    private String title;
    @JacksonXmlProperty(localName = "Description")
    private String description;
    @JacksonXmlProperty(localName = "MusicURL")
    private String musicUrl;
    @JacksonXmlProperty(localName = "HQMusicUrl")
    private String hqMusicUrl;
    @JacksonXmlProperty(localName = "ThumbMediaId")
    private String thumbMediaId;
    @JacksonXmlProperty(localName = "MsgType")
    private MessageType messageType = MessageType.music;
}
