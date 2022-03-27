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

@SpringBootApplication
@RestController
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
                            .hystrix(config ->
                                    config.setName("HystrixCMD").setFallbackUri("forward:/nativeFallback")))
                    .uri("https://example.org")
                    .order(0).id("customer_timer_router")
                ).build();
    }

    // 注册自定义过滤器工厂到 IOC
    @Bean
    public TimeGatewayFilterFactory customerGatewayFilterFactory(){
        return new TimeGatewayFilterFactory();
    }

    @RequestMapping("/nativeFallback")
    public String nativeFallback(){
        return "Hystrix 熔断，本地方法调用。";
    }

}
