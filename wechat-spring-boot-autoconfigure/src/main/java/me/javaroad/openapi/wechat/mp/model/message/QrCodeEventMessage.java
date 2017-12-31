package me.javaroad.openapi.wechat.mp.model.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author heyx
 */
@Getter
@Setter
public class QrCodeEventMessage extends EventMessage {
    @JacksonXmlProperty(localName = "EventKey")
    /**
     * 若是 eventKey 带前缀 "qrscene_" ，则为用户未关注时，关注事件的推送
     * "qrscene_" 后面跟的值即为二维码参数值
     * 此时事件类型为 subscribe
     *
     * 若 eventKey 不带前缀，则为用户已关注时的事件推送
     * KEY 值为创建二维码时的二维码scene_id
     * 此时事件类型为 SCAN
     */
    private String eventKey;
    @JacksonXmlProperty(localName = "Event")
    private Event event;
    @JacksonXmlProperty(localName = "Ticket")
    private String ticket;
}
