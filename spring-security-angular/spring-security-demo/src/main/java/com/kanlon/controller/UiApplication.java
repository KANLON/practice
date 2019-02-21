package com.kanlon.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 测试spring-security的rest功能类
 *
 * @author zhangcanlong
 * @since 2019/2/20 16:38
 **/
@RestController
public class UiApplication {
    @RequestMapping("/resource")
    public Map<String,Object> home() {
        Map<String,Object> model = new HashMap<>(2);
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World");
        return model;
    }
    @RequestMapping("/user")
    public Principal user(Principal user){
        System.out.println("調用user，user是："+user);
        return user;
    }
}
