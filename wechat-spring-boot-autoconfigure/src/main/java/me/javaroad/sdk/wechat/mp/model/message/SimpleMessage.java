package me.javaroad.sdk.wechat.mp.model.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author heyx
 */
@Getter
@Setter
public abstract class SimpleMessage extends AbstractMessage {

    @JacksonXmlProperty(localName = "MsgId")
    private Long messageId;
}
