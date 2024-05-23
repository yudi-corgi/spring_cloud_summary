package com.springcloud.gateway;

import com.springcloud.gateway.filter.TimeFilter;
import com.springcloud.gateway.filter.TimeGatewayFilterFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 由于 Spring Cloud 版本升级（👆2021.0.8），Gateway 关于 Hystrix 相关的配置换成了 Circuit Breaker 和 Resilience4J <br>
 * 因此该类的降级配置是过时的，只是因为时间久远，所以临时做了调整以保证代码正确编译
 */
@Deprecated
@RestController
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /**
     * 自定义转发规则
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        /*
            路由ID：rout_gateway
            匹配路径：/routing
            转发地址：https://example.org

        return builder.routes().route("route_gateway",
                r -> r.path("/routing").uri("https://example.org"))
                .build();*/
        // 使用自定义的过滤器
        return builder.routes()
                .route(r -> r.path("/customer/**")
                    .filters(f -> f.filter(new TimeFilter())
                            .addResponseHeader("X-Response-Name","yudi")
                            .circuitBreaker(config -> config.setName("HystrixCMD").setFallbackUri("forward:/nativeFallback")))
                    .uri("https://example.org")).build();
    }

    // 注册自定义过滤器工厂到 IOC
    @Bean
    public TimeGatewayFilterFactory customerGatewayFilterFactory(){
        return new TimeGatewayFilterFactory();
    }

    @RequestMapping("/nativeFallback")
    public String nativeFallback(){
        return "CircuitBreaker 熔断，本地方法调用。";
    }

}
