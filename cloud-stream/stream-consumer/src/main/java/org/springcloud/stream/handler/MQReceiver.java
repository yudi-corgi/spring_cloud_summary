package org.springcloud.stream.handler;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 消息接收处理器
 * @author YUDI-Corgi
 */
@Component
public class MQReceiver {

    @Bean
    public Consumer<Message<Object>> stringConsumer() {
        return (obj) -> {
            System.out.println("消息消费：" + obj.getPayload());
            MessageHeaders headers = obj.getHeaders();

            Channel amqpChannel = headers.get(AmqpHeaders.CHANNEL, Channel.class);
            Long amqpDeliveryTag = headers.get(AmqpHeaders.DELIVERY_TAG, Long.class);

            int a = 1/0;

            try {
                // 拒绝 ack 且不重入队列，构造死信消息
                assert amqpChannel != null;
                amqpChannel.basicNack(Optional.ofNullable(amqpDeliveryTag).orElse(1L), false, false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
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
            Channel amqpChannel = headers.get(AmqpHeaders.CHANNEL, Channel.class);
            Long amqpDeliveryTag = headers.get(AmqpHeaders.DELIVERY_TAG, Long.class);

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

    /**
     * 函数式接口死信消息处理
     */
    @Bean
    public Consumer<Message<Object>> deadLetterConsumer() {
        return (obj) -> {
            System.out.println("死信交换机处理消息：" + obj.getPayload());
            // obj.getHeaders().forEach((k, v) -> System.out.println(k + ": " + v));
        };
    }

    /**
     * SpringBoot 集成的 MQ 处理方法来处理死信消息
     * @param msg 消息
     */
    // @RabbitListener(bindings = @QueueBinding(
    //         value = @Queue(value = "consumer-demo.dlq"),
    //         exchange = @Exchange("DLX"), key = "consumer-demo"))
    public void handleDlq(org.springframework.amqp.core.Message msg) {
        System.out.println(msg);
    }

    @Bean
    public Consumer<Message<String>> delayConsumer() {
        return (msg) -> {
            System.out.println("当前时间：" + LocalDateTime.now());
            System.out.println(msg.getPayload());
            msg.getHeaders().forEach((k, v) -> System.out.println(k + ": " + v));
        };
    }

    @Bean
    public Consumer<Message<String>> rabbit2Consumer() {
        return (msg) -> System.out.println(msg.getPayload());
    }
}
