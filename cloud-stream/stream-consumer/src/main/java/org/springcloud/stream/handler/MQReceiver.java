package org.springcloud.stream.handler;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

/**
 * 消息接收处理器
 * @author YUDI-Corgi
 */
@Component
public class MQReceiver {

    @StreamListener(Sink.INPUT)
    public void stringConsumer(Object msg) {
        System.out.println("接收到消息：" + msg);
    }

}
