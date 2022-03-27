package com.springcloud.zipkinclientone;

import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@RestController
@EnableHystrix
@EnableDiscoveryClient
public class ZipkinClientOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinClientOneApplication.class, args);
    }

    private static final Logger log = Logger.getLogger(ZipkinClientOneApplication.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Tracer tracer;


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @RequestMapping("/callZipkinTwo")
    public String callZipkinTwo(){
        log.log(Level.INFO, "调用 zipkin-client-two.");
        tracer.currentSpan().tag("zipkin-one","examination");
        return restTemplate.getForObject("http://localhost:8094/getZipkinOneCall", String.class);
    }

    @RequestMapping("/getZipkinTwoCall")
    @NewSpan("logRecord")   // 监控本地方法，开启一个新的 span
    public String info(){
        //代码实现新建 Span
        // ScopedSpan span = tracer.startScopedSpan("logRecord");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.out.println("休眠结束");
        }
        log.log(Level.INFO, "调用 zipkin-client-one 成功.");
        return "调用 zipkin-client-one 成功.";
    }

  /*  @Bean
    public Sampler defaultSampler(){
        return Sampler.ALWAYS_SAMPLE;
    }*/


}
