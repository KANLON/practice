package com.kanlon.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务提供类的controller类，实现hello
 *
 * @author zhangcanlong
 * @since 2019/2/13 16:26
 **/
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String index(@RequestParam String name){
        return "hello "+name+",this is second message";
    }

    @RequestMapping("/test")
    public String test(){
        return "test";
    }
}
