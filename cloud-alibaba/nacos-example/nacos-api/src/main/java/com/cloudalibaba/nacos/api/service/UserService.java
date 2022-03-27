package com.cloudalibaba.nacos.api.service;

import com.cloudalibaba.nacos.api.config.FeignConfig;
import com.cloudalibaba.nacos.api.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author YUDI-Corgi
 * @description
 */
@RestController
@FeignClient(name = "nacos-provider", path = "/user", contextId = "userService", configuration = FeignConfig.class)
public interface UserService {

    /**
     * 根据 ID 获取用户
     * @param id 用户 ID
     * @return {@link User}
     */
    @GetMapping("/{id}")
    User selectById(@PathVariable String id);

    /**
     * 查询所有用户
     * @return 用户列表
     */
    @GetMapping("/all")
    List<User> selectAll();

    /**
     * 保存用户信息
     * @param user 用户信息
     */
    @PostMapping("/save")
    void saveUser(User user);

    /**
     * 根据 ID 删除用户
     * @param id 用户 ID
     */
    @PostMapping("/delete/{id}")
    void deleteById(@PathVariable("id") String id);

    /**
     * 根据 ID 更新用户
     * @param user 需更改的用户信息
     */
    @PostMapping("/update")
    void updateUser(User user);

}
