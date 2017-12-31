package me.javaroad.openapi.wechat.mp.model.message;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author heyx
 * 用户点击自定义菜单后，微信会把点击事件推送给开发者， 请注意，点击菜单弹出子菜单，不会产生上报。
 *
 */
@Getter
@Setter
public class CustomMenuEventMessage extends EventMessage {

    /**
     * 1. 点击菜单拉取消息时的事件推送:
     * - 事件KEY值，与自定义菜单接口中KEY值对应
     * - 事件类型，CLICK
     * 2. 点击菜单跳转链接时的事件推送
     * - 事件KEY值，设置的跳转URL
     * - 事件类型，VIEW
     */
    @JacksonXmlProperty(localName = "EventKey")
    private String eventKey;
    @JacksonXmlProperty(localName = "Event")
    private Event event = Event.CLICK;
}
