package me.javaroad.sdk.wechat.mp.model.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author heyx
 */
@Getter
@Setter
public abstract class AbstractMessage implements Message {
    @JacksonXmlProperty(localName = "ToUserName")
    private String to;
    @JacksonXmlProperty(localName = "FromUserName")
    private String from;
    @JacksonXmlProperty(localName = "URL")
    private String url;
    @JacksonXmlProperty(localName = "CreateTime")
    private Long createTime;

}
