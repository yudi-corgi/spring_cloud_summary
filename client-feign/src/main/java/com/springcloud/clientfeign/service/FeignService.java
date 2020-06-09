package com.springcloud.clientfeign.service;

import com.springcloud.clientfeign.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author YUDI
 * @date 2020/5/26 12:12
 */
// 该注解表示该接口方法具体调用的时哪个服务，参数为服务名
// 会将该接口注册为 FeignClientFactoryBean 类型的 Bean 对象
// fallback 指定熔断器实现的类，响应的接口 fallback 则会调用相应的实现方法
// configuration 指定 Feign 相关配置信息的类，这里是日志配置
@FeignClient(value="client-one",fallback = FeignServiceHystrix.class, configuration = FeignConfiguration.class)
public interface FeignService {

    //此处的接口路径要对应服务中要调用的接口路径
    @RequestMapping(value = "/clientOneView",method = RequestMethod.GET)
    String helloClientOne(@RequestParam("name")String name);

}
