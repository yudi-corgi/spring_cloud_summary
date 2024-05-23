package org.springcloud.stream.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * 分区消费者
 * @author YUDI-Corgi
 */
@Component
public class PartitionedMQReceiver {

    @Bean
    public Consumer<Message<String>> partitionedConsumer() {
        return msg -> {
            System.out.println("分区消费：" + msg.getPayload());
            msg.getHeaders().forEach((k, v) -> System.out.println(k + ":" + v));
        };
    }

}
