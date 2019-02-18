package com.kanlon.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试高可用的配置中心的客户端
 *
 * @author zhangcanlong
 * @since 2019/2/18 16:13
 **/
@RestController
public class HelloController {

    @Value("${neo.hello}")
    private String hello;

    @RequestMapping("/hello")
    public String form(){
        return this.hello;
    }
}
