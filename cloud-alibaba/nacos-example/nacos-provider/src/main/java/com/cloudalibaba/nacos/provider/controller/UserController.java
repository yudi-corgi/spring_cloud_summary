package com.cloudalibaba.nacos.provider.controller;

import com.cloudalibaba.nacos.api.domain.User;
import com.cloudalibaba.nacos.api.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author YUDI-Corgi
 * @description
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/user/{id}")
    public User get(@PathVariable String id) {
        return userService.selectById(id);
    }

    @GetMapping("/user")
    public List<User> getAll() {
        return userService.selectAll();
    }

    @PostMapping("/user/save")
    public void save(@RequestBody User user) {
        userService.saveUser(user);
    }

    @DeleteMapping("/user/{id}")
    public void delete(@PathVariable String id) {
        userService.deleteById(id);
    }

    @PostMapping("/user/update")
    public void update(@RequestBody User user) {
        userService.updateUser(user);
    }
}
