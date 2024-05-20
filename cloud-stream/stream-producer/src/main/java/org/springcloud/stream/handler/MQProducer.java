package org.springcloud.stream.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

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

    /**
     * 多通道输入/输出，使用 Project Reactor 提供的 Flux（0~n）和 Mono（1）对象来封装消息，<br>
     * 并通过 Tuple（元组）来组装多个通道的数据为一体。<br>
     * 该特性主要是为了满足以下情况：<br>
     * a) 大数据场景下数据源无组织，并且包含各类数据元素，而开发者需要对其进行整理；<br>
     * b) 数据聚合：将多个数据源的数据进行合并计算。<br>
     * 以下函数表示接受两个输入（一个 String，一个 Integer）和一个输出（String）
     * @return String
     */
    @Bean
    public Function<Tuple2<Flux<String>, Flux<Integer>>, Flux<String>> multipleGather() {
        return tuple -> {
            Flux<String> t1 = tuple.getT1();
            Flux<Integer> t2 = tuple.getT2();
            System.out.println("数据通道一：" + t1 + "，数据通道二：" + t2);
            // 这里是将两个通道的数据连接在一起
            return Flux.combineLatest(t1, t2, (k1, k2) -> k1 + k2.toString());
        };
    }

    /**
     * 多输出通道，官方示例
     * @return String + String
     */
    @Bean
    public Function<Flux<Integer>, Tuple2<Flux<Message<String>>, Flux<Message<String>>>> multipleScatter() {
        return flux -> {
            Flux<Integer> connectedFlux = flux.publish().autoConnect(2);
            // Sinks.UnicastSpec even = Sinks.many().unicast();
            // Sinks.UnicastSpec odd = Sinks.many().unicast();
            UnicastProcessor even = UnicastProcessor.create();
            UnicastProcessor odd = UnicastProcessor.create();
            Flux<Integer> evenFlux = connectedFlux.filter(number -> number % 2 == 0).doOnNext(number -> even.onNext("EVEN: " + number));
            Flux<Integer> oddFlux = connectedFlux.filter(number -> number % 2 != 0).doOnNext(number -> odd.onNext("ODD: " + number));

            // 由以下两者的对比可得出：多输出通道时，元组包含数据的顺序对应输出通道的顺序，可依此来确定数据要发送到哪个输出通道
            // return Tuples.of(Flux.from(even).doOnSubscribe(x -> evenFlux.subscribe()), Flux.from(odd).doOnSubscribe(x -> oddFlux.subscribe()));
            // return Tuples.of(Flux.from(odd).doOnSubscribe(x -> oddFlux.subscribe()), Flux.from(even).doOnSubscribe(x -> evenFlux.subscribe()));

            // 这里只是演示下如何从 Flux 中提取数据为消息设置路由 Key
            Flux oddWithKey = Flux.from(odd).doOnSubscribe(x -> oddFlux.subscribe())
                    .map(data -> {
                        Message<Object> build = MessageBuilder.withPayload(data)
                                .setHeader("rout", "one").build();
                        System.out.println(build);
                        return build;
                    });
            Flux evenWithKey = Flux.from(even).doOnSubscribe(x -> evenFlux.subscribe())
                    .map(data -> {
                        Message<Object> build = MessageBuilder.withPayload(data)
                                .setHeader("rout", "two").build();
                        System.out.println(build);
                        return build;
                    });
            return Tuples.of(oddWithKey, evenWithKey);
        };
    }

}
