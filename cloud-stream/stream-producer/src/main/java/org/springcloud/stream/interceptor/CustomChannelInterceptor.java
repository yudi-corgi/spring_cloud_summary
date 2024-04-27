package org.springcloud.stream.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

/**
 * 消息通道拦截器
 * @author YUDI-Corgi
 */
@Component
public class CustomChannelInterceptor {

    /**
     * <code>@GlobalChannelInterceptor</code> 注解定义了哪些绑定通道在发送消息时会被拦截，默认 * 匹配全部
     * @return ChannelInterceptor
     */
    @Bean
    @GlobalChannelInterceptor(patterns = "stringConsumer-*")
    public ChannelInterceptor consumerChannelInterceptor() {
        return new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                System.out.println("消息发送前输出内容：" + message.getPayload());
                // 如果返回 null 则不会真正发送消息
                return message;
            }

            @Override
            public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
                System.out.println("消息发送后输出内容：" + message.getPayload());
            }
        };
    }

}
