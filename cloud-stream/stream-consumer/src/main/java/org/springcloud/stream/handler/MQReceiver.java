package org.springcloud.stream.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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
    public Consumer<Object> stringSupplier() {
        return (obj) -> System.out.println("toUpperCase 接收到消息：" + obj.toString().toUpperCase());
    }

    @Bean
    public Consumer<Object> msgPrinter() {
        return (obj) -> System.out.println("upperToString 接收到消息：" + obj.toString());
    }

}
