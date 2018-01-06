package me.javaroad.sdk.wechat.mp.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import me.javaroad.sdk.wechat.exception.WeChatApiException;
import me.javaroad.sdk.wechat.mp.config.WeChatMpProperties;
import me.javaroad.sdk.wechat.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * @author heyx
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WeChatApiRequest {

    private final RestTemplate restTemplate;
    private final WeChatMpProperties mpProperties;

    public <T> T get(String url, Class<T> clazz, Object... params) {
        try {
            ResponseEntity<T> responseEntity = restTemplate.getForEntity(url, clazz, params);
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                return responseEntity.getBody();
            }
            throw new WeChatApiException("call url[" + url + "] failed");
        } catch (RestClientException e) {
            throw new WeChatApiException("call url[" + url + "] failed, messase: " + e.getMessage());
        }
    }

    public <T, R> R post(String url, T t, Class<R> clazz) {
        String paramJsonString = "post param logs are not enabled";
        try {
            ResponseEntity<R> responseEntity = restTemplate.postForEntity(url, t, clazz);
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)
                || responseEntity.getStatusCode().equals(HttpStatus.CREATED)) {
                return responseEntity.getBody();
            }
            if (mpProperties.getLog().getLogPostParam()) {
                try {
                    paramJsonString = JsonUtils.jsonString(t);
                } catch (JsonProcessingException e) {
                    paramJsonString = "convert post param to json failed";
                }
            }
            throw new WeChatApiException("call url[" + url + "] failed, post param: " + paramJsonString);
        } catch (RestClientException e) {
            throw new WeChatApiException(
                "call url[" + url + "] failed, message: " + e.getMessage() + ", postParam:" + paramJsonString);
        }
    }

    public <T, R> R post(String url, MultiValueMap<String, String> headers, T body, Class<R> clazz) {
        String paramJsonString = "post param logs are not enabled";
        try {
            HttpEntity<T> httpEntity = new HttpEntity<>(body, headers);
            ResponseEntity<R> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, clazz);
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)
                || responseEntity.getStatusCode().equals(HttpStatus.CREATED)) {
                return responseEntity.getBody();
            }
            if (mpProperties.getLog().getLogPostParam()) {
                try {
                    if (Objects.nonNull(body)) {
                        paramJsonString = JsonUtils.jsonString(body);
                    }
                } catch (JsonProcessingException e) {
                    paramJsonString = "convert post param to json failed";
                }
            }
            throw new WeChatApiException("call url[" + url + "] failed, post param: " + paramJsonString);
        } catch (RestClientException e) {
            throw new WeChatApiException(
                "call url[" + url + "] failed, message: " + e.getMessage() + ", postParam:" + paramJsonString);
        }
    }
}
