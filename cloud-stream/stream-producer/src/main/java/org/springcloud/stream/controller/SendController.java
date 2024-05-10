package org.springcloud.stream.controller;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author YUDI-Corgi
 */
@RestController
@RequestMapping("/send")
public class SendController {

    @Resource
    private StreamBridge streamBridge;

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
        // StreamBridge：一个允许用户将数据发送到输出绑定的类
        streamBridge.send("function-topic", msg, MimeType.valueOf("application/json"));
    }

    @GetMapping("/lower")
    public void lower() {
        // 构建消息
        Message<String> msg = MessageBuilder.withPayload("LOWER AnyThing.").build();
        // StreamBridge：一个允许用户将数据发送到输出绑定的类
        streamBridge.send("trim-lower-topic", msg, MimeType.valueOf("application/json"));
    }

}
