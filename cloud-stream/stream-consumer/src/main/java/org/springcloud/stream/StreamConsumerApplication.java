package org.springcloud.stream;

import org.springcloud.stream.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

/**
 * @author YUDI-Corgi
 */
@SpringBootApplication
public class StreamConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamConsumerApplication.class, args);
    }

    @Bean
    public Consumer<User> log() {
        return user -> System.out.println("Received: " + user.toString());
    }

}
