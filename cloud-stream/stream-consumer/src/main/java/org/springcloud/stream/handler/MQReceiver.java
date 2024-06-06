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
            Thread currentThread = Thread.currentThread();
            System.out.println("当前线程名称：" + currentThread.getName());
            System.out.println("当前线程 ID：" + currentThread.getId());
            System.out.println("消息消费：" + obj.getPayload());
            MessageHeaders headers = obj.getHeaders();

            // 注意：要获取 Channel 对象除了开启手动 ACK 之外，发送消息时 StreamBridge 必须指定的是对应的输出绑定或动态目的地
            // 如果指定输入绑定，那么获取的 MessageHeaders 将只有几个基础信息而已
            Channel amqpChannel = headers.get(AmqpHeaders.CHANNEL, Channel.class);
            Long amqpDeliveryTag = headers.get(AmqpHeaders.DELIVERY_TAG, Long.class);

            // int a = 1/0;

            try {
                assert amqpChannel != null;
                // 拒绝 ack 且不重入队列，构造死信消息
                // amqpChannel.basicNack(Optional.ofNullable(amqpDeliveryTag).orElse(1L), false, false);
                amqpChannel.basicAck(Optional.ofNullable(amqpDeliveryTag).orElse(1L), false);
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

    @Bean
    public Consumer<Message<String>> kafkaConsumer() {
        return (msg) -> {
            System.out.println("Kafka 消费：" + msg.getPayload());
            msg.getHeaders().forEach((k, v) -> System.out.println(k + ": " + v));
        };
    }

    @Bean
    public Consumer<Message<String>> gatherConsumer() {
        return msg -> System.out.println("多输入通道整合后的数据：" + msg.getPayload());
    }

    @Bean
    public Consumer<Message<String>> scatterConsumerOne() {
        return msg -> {
            System.out.println("多输出通道一：" + msg.getPayload());
            System.out.println("通道路由 Key：" + msg.getHeaders().get("rout"));
        };
    }

    @Bean
    public Consumer<Message<String>> scatterConsumerTwo() {
        return msg -> {
            System.out.println("多输出通道二：" + msg.getPayload());
            System.out.println("通道路由 Key：" + msg.getHeaders().get("rout"));
        };
    }

}
