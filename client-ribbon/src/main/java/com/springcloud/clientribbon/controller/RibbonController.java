package com.springcloud.clientribbon.controller;

import com.springcloud.clientribbon.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 控制层
 * @author YUDI
 * @date 2020/5/26 11:52
 */
@Controller
public class RibbonController {

    @Autowired
    private RibbonService ribbonService;

    @GetMapping("/ribbonClient")
    @ResponseBody
    public String hello(@RequestParam("name")String name){
        return ribbonService.helloService(name);
    }

}
