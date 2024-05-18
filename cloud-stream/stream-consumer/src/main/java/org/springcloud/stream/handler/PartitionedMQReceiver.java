package org.springcloud.stream.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 分区消费者
 * @author YUDI-Corgi
 */
@Component
public class PartitionedMQReceiver {

    @Bean
    public Consumer<Message<String>> consumerOne() {
        return msg -> {
            System.out.println("消息分区一：" + msg.getPayload());
            msg.getHeaders().forEach((k, v) -> {
                System.out.println(k + ":" + v);
            }
        }
    }

    @Bean
    public Consumer<Message<String>> consumerTwo() {
        return msg -> {
            System.out.println("消息分区二：" + msg.getPayload());
            msg.getHeaders().forEach((k, v) -> {
                System.out.println(k + ":" + v);
            }
        }
    }

}
