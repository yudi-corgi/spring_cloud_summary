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
        return (obj) -> System.out.println("接收到消息：" + obj);
    }

}
