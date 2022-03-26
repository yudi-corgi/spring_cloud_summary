package com.cloudalibaba.nacos.consumer.controller;

import com.cloudalibaba.nacos.consumer.domain.User;
import com.cloudalibaba.nacos.consumer.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author YUDI-Corgi
 * @description
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/{id}")
    private User get(@PathVariable("id") String id) {
        return userService.get(id);
    }

}
