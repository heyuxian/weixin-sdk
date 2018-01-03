package me.javaroad.sdk.wechat.mp;

import me.javaroad.sdk.wechat.mp.event.ReceiveMessageEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author heyx
 */
@Component
public class ReceiveMessageEventListener implements ApplicationListener<ReceiveMessageEvent> {

    @Override
    public void onApplicationEvent(ReceiveMessageEvent event) {
        System.out.println(event.getSource());
    }
}
