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
        Message<String> msg = MessageBuilder.withPayload(name).build();
        // StreamBridge：一个允许用户将数据发送到输出绑定的类
        streamBridge.send("stringConsumer-out-0", msg, MimeType.valueOf("application/json"));
    }

    @GetMapping("/upper")
    public void test() {
        // 构建消息
        Message<String> msg = MessageBuilder.withPayload("upper nothing").build();
        // StreamBridge：一个允许用户将数据发送到输出绑定的类
        streamBridge.send("toUpperCase-in-0", msg, MimeType.valueOf("application/json"));
    }

}
