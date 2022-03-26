package com.cloudalibaba.nacos.consumer.service;

import com.cloudalibaba.nacos.consumer.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author YUDI-Corgi
 * @description User Feign 接口，应当抽象到单独服务模块，避免耦合到某一服务消费者后，导致其它消费者需要调用时重复创建相同 Feign 接口
 */
@FeignClient(name = "nacos-provider", path = "/user", contextId = "userService")
public interface UserService {

    /**
     * 根据 ID 获取用户
     * @param id 用户 ID
     * @return {@link User}
     */
    @GetMapping("/{id}")
    User get(@PathVariable String id);
}
