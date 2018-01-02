package me.javaroad.openapi.wechat.mp.config;

import static me.javaroad.openapi.wechat.mp.WeChatConstants.ENCRYPT_MESSAGE_PARAM;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

import java.io.BufferedReader;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import me.javaroad.openapi.wechat.mp.model.message.AbstractMessage;
import me.javaroad.openapi.wechat.mp.model.message.EncryptMessage;
import me.javaroad.openapi.wechat.utils.MessageUtils;
import me.javaroad.openapi.wechat.utils.XmlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author heyx
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private WeChatMpProperties mpProperties;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new WeChatMessageResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    class WeChatMessageResolver implements HandlerMethodArgumentResolver {

        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return AbstractMessage.class.isAssignableFrom(parameter.getParameterType());
        }

        @Override
        public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

            BufferedReader buffer = webRequest.getNativeRequest(HttpServletRequest.class).getReader();

            StringBuilder builder = new StringBuilder();
            String temp;
            while ((temp = buffer.readLine()) != null) {
                builder.append(temp);
            }
            String xmlMessageContent = builder.toString();
            if (MessageUtils.isEncrypt(xmlMessageContent)) {
                EncryptMessage encryptMessage = EncryptMessage.builder()
                    .nonce(webRequest.getParameter("nonce"))
                    .timestamp(Long.valueOf(webRequest.getParameter("timestamp")))
                    .signature(webRequest.getParameter("msg_signature"))
                    .encrypt(XmlUtils.readNode(builder.toString(), "Encrypt"))
                    .build();
                xmlMessageContent = MessageUtils.decryptMessage(encryptMessage, mpProperties);
                webRequest.setAttribute(ENCRYPT_MESSAGE_PARAM, true, SCOPE_REQUEST);
            }
            return MessageUtils.buildMessage(xmlMessageContent);
        }
    }
}
