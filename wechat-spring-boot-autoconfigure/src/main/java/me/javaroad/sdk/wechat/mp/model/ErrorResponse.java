package me.javaroad.sdk.wechat.mp.model;

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
