package org.springcloud.stream.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 消息生产者
 * @author YUDI-Corgi
 */
@Component
public class MQProducer {

    @Bean
    public Supplier<Message<String>> stringSupplier() {
        return () -> MessageBuilder.withPayload("Hello, Spring Cloud Stream!")
                .setHeader("routingKey", "string-supplier-key")
                .build();
    }

    @Bean
    public Function<String, String> toUpperCase() {
        return String::toUpperCase;
    }

    @Bean
    public Function<String, String> addDash() {
        return s -> s.replace(" ", "-");
    }

    @Bean
    public Function<String, String> toTrim() {
        return String::toLowerCase;
    }

    @Bean
    public Consumer<String> allPrinter() {
        return System.out::println;
    }

}
