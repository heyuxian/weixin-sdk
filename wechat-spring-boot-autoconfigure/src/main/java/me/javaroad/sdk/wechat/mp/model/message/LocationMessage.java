package me.javaroad.sdk.wechat.mp.model.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author heyx
 */
@Getter
@Setter
public class LocationMessage extends SimpleMessage {

    @JacksonXmlProperty(localName = "Location_X")
    private String locationX;
    @JacksonXmlProperty(localName = "Location_Y")
    private String locationY;
    @JacksonXmlProperty(localName = "Scale")
    private Integer scale;
    @JacksonXmlProperty(localName = "Label")
    private String label;
    @JacksonXmlProperty(localName = "MsgType")
    private MessageType messageType = MessageType.location;
}
