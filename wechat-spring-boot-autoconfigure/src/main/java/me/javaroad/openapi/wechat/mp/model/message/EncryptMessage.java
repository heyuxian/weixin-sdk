package me.javaroad.openapi.wechat.mp.model.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author heyx
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JacksonXmlRootElement(localName = "xml")
public class EncryptMessage {
    @JacksonXmlProperty(localName = "Encrypt")
    private String encrypt;
    @JacksonXmlProperty(localName = "AppId")
    private String appid;
    @JacksonXmlProperty(localName = "ToUserName")
    private String to;
    @JacksonXmlProperty(localName = "MsgSignature")
    private String signature;
    @JacksonXmlProperty(localName = "TimeStamp")
    private Long timestamp;
    @JacksonXmlProperty(localName = "Nonce")
    private String nonce;
}
