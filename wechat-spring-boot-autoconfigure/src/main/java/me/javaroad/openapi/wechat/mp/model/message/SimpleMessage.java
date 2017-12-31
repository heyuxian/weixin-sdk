package me.javaroad.openapi.wechat.mp.model.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author heyx
 */
@Getter
@Setter
public abstract class SimpleMessage extends BaseMessage {

    @JacksonXmlProperty(localName = "MsgId")
    private Long messageId;
}
