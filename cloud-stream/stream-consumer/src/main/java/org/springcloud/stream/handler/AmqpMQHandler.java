package org.springcloud.stream.handler;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;

import java.io.IOException;

// @Component
public class AmqpMQHandler {

    /**
     * SpringBoot 集成的 MQ 处理注解来处理 Stream 构建的死信队列里的死信消息
     * @param msg 消息
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "consumer-demo.dlq", arguments = @Argument(name = "x-max-length", value = "10")),
            exchange = @Exchange("DLX"), key = "consumer-demo"))
    public void handleDlq(org.springframework.amqp.core.Message msg) {
        System.out.println(msg);
    }

    /**
     * 多个队列绑定由同一消息监听器处理
     * @param msg     消息
     * @param channel AMQP通道
     * @throws IOException IOException
     */
    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue(value = "consumerOne-queue"),
                    exchange = @Exchange(value = "consumerOne-exchange", type = ExchangeTypes.TOPIC), key = ""),
            @QueueBinding(value = @Queue(value = "consumerTwo-queue"),
                    exchange = @Exchange(value = "consumerTwo-exchange", type = ExchangeTypes.TOPIC), key = "")
    })
    public void handleMsg(org.springframework.amqp.core.Message msg, Channel channel) throws IOException {
        System.out.println("一个处理器匹配多个队列：" + msg);
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
    }

}
