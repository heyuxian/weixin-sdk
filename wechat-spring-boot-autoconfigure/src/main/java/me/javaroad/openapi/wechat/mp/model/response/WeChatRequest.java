package me.javaroad.openapi.wechat.mp.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author heyx
 */
@Getter
@Setter
public class WeChatRequest {
    private String signature;
    private String nonce;
    private String echostr;
    private Long timestamp;
}
