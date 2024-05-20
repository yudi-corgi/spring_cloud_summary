package org.springcloud.stream.controller;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author YUDI-Corgi
 */
@RestController
@RequestMapping("/send")
public class SendController {

    @Resource
    private StreamBridge streamBridge;
    @Resource
    private AtomicInteger index;

    @GetMapping("/test")
    public void test(String name) {
        // 构建消息
        Message<String> msg = MessageBuilder
                .withPayload(name)
                // headers 属性 type 指定路由键，该属性的设置看 application.yml
                .setHeader("type", "string-consumer-key")
                .build();
        // StreamBridge：一个允许用户将数据发送到输出绑定的类
        // 可以指定 destination 也可以指定绑定名称
        // 若直接指定 destination，其实会动态创建输入/输出绑定，而绑定的 destination 自然就是指定的 destination，如 consumer-topic
        // 而在消费者服务则是刚好有 Consumer Bean 的输入绑定声明了相同的 destination，所以消息自然就被消费了
        // 而若指定的是配置好的输出通道，则直接通过绑定关系路由消息
        // streamBridge.send("consumer-topic", msg, MimeType.valueOf("application/json"));
        streamBridge.send("stringConsumer-out-0", msg, MimeType.valueOf("application/json"));
    }

    @GetMapping("/upper")
    public void upper() {
        // 构建消息
        Message<String> msg = MessageBuilder.withPayload("upper nothing").build();
        streamBridge.send("function-topic", msg, MimeType.valueOf("application/json"));
    }

    @GetMapping("/lower")
    public void lower() {
        // 构建消息
        Message<String> msg = MessageBuilder.withPayload("LOWER AnyThing.").build();
        streamBridge.send("trim-lower-topic", msg, MimeType.valueOf("application/json"));
    }

    @GetMapping("/delay")
    public void delay() {
        Message<String> msg = MessageBuilder.withPayload("This is a delay msg.")
                // .setHeader("x-delay", 3000)
                // .setHeader("first", 2)
                .build();
        System.out.println("当前时间：" + LocalDateTime.now());
        streamBridge.send("delayConsumer-out-0", msg);
    }

    @GetMapping("/sendAnother")
    public void sendAnother() {
        Message<String> msg = MessageBuilder.withPayload("Hello, Rabbit 2").build();
        streamBridge.send("rabbit2Consumer-out-0", msg);
    }

    @GetMapping("/prepare")
    public void prepare() {
        Message<String> msg = MessageBuilder.withPayload("Hello, prepare queue.").build();
        streamBridge.send("prepareQueue-out-0", msg);
    }

    @GetMapping("/kafka/consumer")
    public void kafkaConsumer() {
        Message<String> msg = MessageBuilder.withPayload("Hello, Kafka consumer.").build();
        streamBridge.send("kafkaConsumer-out-0", msg);
    }

    @GetMapping("/partitioned")
    public void partitioned() {
        Message<String> msg = MessageBuilder.withPayload("Hello, partitioned consumer.")
                // .setHeader("index", index.incrementAndGet())
                .setHeader("partition-index", index.incrementAndGet())
                .build();
        streamBridge.send("partitionedConsumer-out-0", msg);
    }

    @GetMapping("/multiple")
    public void multiple() {
        Message<String> msg1 = MessageBuilder.withPayload("Hello, multiple channel.").build();
        Message<Integer> msg2 = MessageBuilder.withPayload(index.getAndIncrement()).build();
        streamBridge.send("multipleGather-in-0", msg1);
        streamBridge.send("multipleGather-in-1", msg2);
    }

}
