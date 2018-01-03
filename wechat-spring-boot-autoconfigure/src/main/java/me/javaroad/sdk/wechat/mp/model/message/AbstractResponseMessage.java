package me.javaroad.sdk.wechat.mp.model.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * @author heyx
 */
@JacksonXmlRootElement(localName = "xml")
public abstract class AbstractResponseMessage extends AbstractMessage implements ResponseMessage {

}
