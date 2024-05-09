package org.springcloud.stream.handler;

import com.rabbitmq.client.Channel;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 消息接收处理器
 * @author YUDI-Corgi
 */
@Component
public class MQReceiver {

    @Bean
    public Consumer<Object> stringConsumer() {
        return (obj) -> System.out.println("消息消费：" + obj);
    }

    /**
     * 消费 Producer 中 stringSupplier 发送的消息
     */
    @Bean
    public Consumer<Message<Object>> stringSupplier() {
        return (obj) -> {
            System.out.println("toUpperCase 接收到消息：" + obj.getPayload());
            // 如果需要手动应答、或者获取连接通道本身信息的，则需要依赖 Message<T> 对象
            MessageHeaders headers = obj.getHeaders();
            Channel amqpChannel = headers.get("amqp_channel", Channel.class);
            Long amqpDeliveryTag = headers.get("amqp_deliveryTag", Long.class);

            try {
                // 手动应答
                assert amqpChannel != null;
                amqpChannel.basicAck(Optional.ofNullable(amqpDeliveryTag).orElse(1L), false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Bean
    public Consumer<Object> msgPrinter() {
        return (obj) -> System.out.println("upperToString 接收到消息：" + obj.toString());
    }

}
