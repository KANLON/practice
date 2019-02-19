package com.kanlon.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping("/hello")
    public String index(@RequestParam String name){
        logger.info("request two name is "+name);
        try{
            Thread.sleep(1000000);
        }catch ( Exception e){
            logger.error(" hello two error",e);
        }
        return "hello "+name+",this is second message";
    }

    @RequestMapping("/test")
    public String test(){
        return "test";
    }
}