package me.javaroad.openapi.wechat.mp.event;

import me.javaroad.openapi.wechat.BaseSpringMvcTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

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
      /*  String messageXml =
            "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>"
                + "<CreateTime>1348831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content>"
                + "<![CDATA[thisisatest]]></Content><MsgId>1234567890123456</MsgId></xml>";
        ResponseEntity<String> responseEntity = restTemplate
            .postForEntity("/wechat/callback", messageXml, String.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotEqualTo("error");*/

        /*verify(eventListener).onApplicationEvent(argThat(new ArgumentMatcher<ReceiveMessageEvent>() {
            @Override
            public boolean matches(Object argument) {
                ReceiveMessageEvent messageEvent = (ReceiveMessageEvent) argument;
                assertThat(messageEvent.getSource() instanceof TextMessage).isTrue();
                return true;
            }
        }));*/
    }

}