package org.springcloud.stream.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 消息生产者
 * @author YUDI-Corgi
 */
@Component
public class MQProducer {

    @Bean
    public Supplier<String> stringSupplier() {
        return () -> "Hello, Spring Cloud Stream!";
    }

    @Bean
    public Function<String, String> toUpperCase() {
        return String::toUpperCase;
    }

}
