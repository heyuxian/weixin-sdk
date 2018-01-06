package me.javaroad.sdk.wechat.mp.service;

import lombok.RequiredArgsConstructor;
import me.javaroad.sdk.wechat.mp.config.WeChatMpProperties;
import me.javaroad.sdk.wechat.mp.http.WeChatApiRequest;
import me.javaroad.sdk.wechat.mp.model.upload.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

/**
 * @author heyx
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UploadService {

    private final WeChatApiRequest apiRequest;
    private final WeChatMpProperties mpProperties;
    private final AccessTokenService accessTokenService;

    public ImageResponse uploadImage() {
        HttpHeaders headers = new HttpHeaders();
        String url = accessTokenService.buildTokenUrl(mpProperties.getEndpoint().getUploadImageUrl());
        // todo
        return null;
    }
}
