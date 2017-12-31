package me.javaroad.openapi.wechat.mp.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author heyx
 */
@Getter
@Setter
public class ErrorResponse {
    private String errcode;
    private String errmsg;
}
