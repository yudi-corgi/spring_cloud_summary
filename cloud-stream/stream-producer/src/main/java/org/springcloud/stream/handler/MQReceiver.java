package org.springcloud.stream.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * 消息消费者
 */
@Component
public class MQReceiver {

    @Bean
    public Consumer<String> allPrinter() {
        return System.out::println;
    }

}
