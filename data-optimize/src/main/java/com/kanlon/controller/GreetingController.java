package com.kanlon.controller;

import com.kanlon.hello.Greeting;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * 测试多媒体超链接restful的controller
 *
 * @author zhangcanlong
 * @since 2019/4/2 20:33
 **/
@RestController
public class GreetingController {


    private static final String TEMPLATE = "Hello, %s!";

    /**
     * 超媒体链接的restful
     * @param name 发送过来名字参数
     * @return 包含链接的信息
     **/
    @RequestMapping("/greeting")
    public HttpEntity<Greeting> greeting(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

        Greeting greeting = new Greeting(String.format(TEMPLATE, name));

        greeting.add(linkTo(methodOn(GreetingController.class).greeting(name)).withSelfRel());

        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }
}
