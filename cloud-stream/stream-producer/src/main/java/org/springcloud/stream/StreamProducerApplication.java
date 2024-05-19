package org.springcloud.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.binder.PartitionKeyExtractorStrategy;
import org.springframework.cloud.stream.binder.PartitionSelectorStrategy;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author YUDI-Corgi
 */
@SpringBootApplication
public class StreamProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamProducerApplication.class, args);
    }

    @Bean
    public AtomicInteger index() {
        return new AtomicInteger(0);
    }

    /**
     * 自定义分区键提取策略
     * @return PartitionKeyExtractorStrategy
     */
    @Bean
    public PartitionKeyExtractorStrategy customKeyExtractorStrategy() {
        // 这里简单演示，直接取 header 属性作为分区键，使用时根据实际情况编写分区键计算逻辑
        return message -> message.getHeaders().get("partition-index");
    }

    /**
     * 自定义分区 ID 选择策略
     * @return PartitionSelectorStrategy
     */
    @Bean
    public PartitionSelectorStrategy customSelectorStrategy() {
        return (key, partitionCount) -> {
            // key 参数即分区键提取策略返回的分片键，partitionCount 自然就是分区数量了
            System.out.println("分区键：" + key);
            return key.hashCode() % partitionCount;
        };
    }

}
