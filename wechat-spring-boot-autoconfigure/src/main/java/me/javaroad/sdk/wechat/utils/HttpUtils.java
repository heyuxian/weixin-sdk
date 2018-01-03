package me.javaroad.sdk.wechat.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.javaroad.sdk.wechat.exception.ApiException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

/**
 * @author heyx
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUtils {

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");

    static {
        // todo config http client here
    }

    public static <T> T get(String url, Class<T> clazz) {
        Request request = new Request.Builder()
            .url(url)
            .build();
        return call(url, request, clazz);
    }

    public static <T, M> T post(String url, M body, Class<T> clazz) {
        RequestBody requestBody;
        try {
            requestBody = RequestBody.create(JSON, JsonUtils.jsonString(body));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("parse object to json failed, url=" + url);
        }
        Request request = new Request.Builder()
            .url(url)
            .post(requestBody)
            .build();
        return call(url, request, clazz);
    }

    private static <T> T call(String url, Request request, Class<T> clazz) {
        Response response;
        String responseStr;
        try {
            response = HTTP_CLIENT.newCall(request).execute();
            if (Objects.isNull(response.body())) {
                return null;
            }
            responseStr = response.body().string();
            if (StringUtils.isBlank(responseStr)) {
                return null;
            }
        } catch (IOException e) {
            throw new ApiException("call url: " + url + "failed", e);
        }
        try {
            return JsonUtils.parse(responseStr, clazz);
        } catch (IOException e) {
            // request param error or remote server error
            log.error(responseStr);
            throw new ApiException("call url: " + url + "failed", e);
        }
    }
}
