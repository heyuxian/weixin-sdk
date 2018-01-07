package me.javaroad.sdk.wechat.mp.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;

import me.javaroad.sdk.wechat.mp.model.message.TextMessage;
import me.javaroad.test.BaseSpringMvcTest;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author heyx
 */
public class ReceiveMessageEventListenerTest extends BaseSpringMvcTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @MockBean
    private ReceiveMessageEventListener eventListener;

    @Test
    public void onApplicationEvent() throws Exception {
        String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
                + "<CreateTime>1348831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content>"
                + "<![CDATA[thisisatest]]></Content><MsgId>1234567890123456</MsgId></xml>";
        ResponseEntity<String> responseEntity = restTemplate
            .postForEntity("/weixin/callback", messageXml, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotEqualTo("error");

        verify(eventListener).onApplicationEvent(argThat(new ArgumentMatcher<ReceiveMessageEvent>() {
            @Override
            public boolean matches(Object argument) {
                ReceiveMessageEvent messageEvent = (ReceiveMessageEvent) argument;
                assertThat(messageEvent.getSource() instanceof TextMessage).isTrue();
                return true;
            }
        }));
    }

}