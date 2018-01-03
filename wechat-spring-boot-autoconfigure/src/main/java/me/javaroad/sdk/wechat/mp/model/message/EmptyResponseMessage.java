package me.javaroad.sdk.wechat.mp.model.message;

import lombok.Getter;
import lombok.Setter;

/**
 * @author heyx
 * 因为微信对无默认回复的消息做的特殊处理：
 * - 回复 "success" 或者是 ""（空字符串）
 * 导致此处必须做特殊处理，非常不优雅，略坑
 */
@Getter
@Setter
public class EmptyResponseMessage extends AbstractResponseMessage {

    @Override
    public MessageType getMessageType() {
        return null;
    }

    @Override
    public void setCreateTime(Long createTime) {
        throw new UnsupportedOperationException("Unsupported Operation");
    }

    @Override
    public void setFrom(String from) {
        throw new UnsupportedOperationException("Unsupported Operation");
    }

    @Override
    public void setTo(String to) {
        throw new UnsupportedOperationException("Unsupported Operation");
    }
}
