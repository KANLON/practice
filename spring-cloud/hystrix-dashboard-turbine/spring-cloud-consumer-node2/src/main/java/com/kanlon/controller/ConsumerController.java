package com.kanlon.controller;

import com.kanlon.remote.HelloRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 服务消费者，消费的controller
 *
 * @author zhangcanlong
 * @since 2019/2/13 16:47
 **/
@RestController
public class ConsumerController {

    /**
     * 由于 FeignClient隐性注入，所以使用@Resource通过byName注入更能精确查找。参考：https://www.cnblogs.com/think-in-java/p/5474740.html
     **/
    @Resource(name="com.kanlon.remote.HelloRemote")
    HelloRemote helloRemote;

    @RequestMapping("/hello/{name}")
    public String index(@PathVariable("name") String name){
        return helloRemote.index(name);
    }

}
