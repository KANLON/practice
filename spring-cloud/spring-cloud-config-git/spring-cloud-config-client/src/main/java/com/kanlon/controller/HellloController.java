package com.kanlon.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 測試获取spring config配置中心的数据
 *
 * @author zhangcanlong
 * @since 2019/2/18 14:43
 **/
@RestController
public class HellloController {

    @Value("${neo.hello}")
    private String hello;

    @RequestMapping("/hello")
    public String from(){
        return this.hello;
    }
}
