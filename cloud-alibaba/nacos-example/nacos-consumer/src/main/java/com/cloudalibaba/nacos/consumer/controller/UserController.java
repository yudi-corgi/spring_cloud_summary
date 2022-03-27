package com.cloudalibaba.nacos.consumer.controller;

import com.cloudalibaba.nacos.api.domain.User;
import com.cloudalibaba.nacos.api.service.UserService;
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

    @GetMapping("/get/{id}")
    private User get(@PathVariable("id") String id) {
        return userService.selectById(id);
    }

}
