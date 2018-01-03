package me.javaroad.sdk.wechat.mp.model.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author heyx 用户同意上报地理位置后，每次进入公众号会话时，
 *
 * 都会在进入时上报地理位置， 或在进入会话后每5秒上报一次地理位置，公众号可以在公众平台网站中修改以上设置。
 * 上报地理位置时，微信会将上报地理位置事件推送到开发者填写的URL。
 */
@Getter
@Setter
public class LocationEventMessage extends EventMessage {

    @JacksonXmlProperty(localName = "Event")
    private Event event = Event.LOCATION;
    @JacksonXmlProperty(localName = "Latitude")
    private String latitude;
    @JacksonXmlProperty(localName = "Longitude")
    private String longitude;
    @JacksonXmlProperty(localName = "Precision")
    private String precision;
}
