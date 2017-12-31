package me.javaroad.openapi.wechat.mp.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author heyx
 */
@Component
public class ReceiveMessageEventListener implements ApplicationListener<ReceiveMessageEvent> {

    @Override
    public void onApplicationEvent(ReceiveMessageEvent event) {
        //根据自己的业务逻辑进行特殊处理
    }
}
