package com.kanlon.controller;

import com.kanlon.model.CommonResponse;
import com.kanlon.model.ConsumerRequest;
import com.kanlon.service.GrpcConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 测试远程调用的方法的测试controller
 *
 * @author zhangcanlong
 * @since 2019/5/4 17:36
 **/
@RestController
@RequestMapping("/")
public class TestController {

    @Autowired
    private GrpcConsumerService grpcConsumerService;

    @GetMapping("/hello")
    public CommonResponse testHello(@RequestParam(defaultValue = "参数1") String param1, @RequestParam(defaultValue =
            "参数2") String param2, @RequestParam(defaultValue = "localhost") String providerName) {
        ConsumerRequest request = new ConsumerRequest();
        request.setParam1(param1);
        request.setParam2(param2);
        request.setProviderName(providerName);
        request.setType("http");
        return grpcConsumerService.callMethod(request);
    }

    /**
     * 调用参数
     *
     * @param request 请求参数
     * @return com.kanlon.model.CommonResponse
     **/
    @PostMapping("/task")
    public CommonResponse testTask(@RequestBody @Valid ConsumerRequest request) {
        return grpcConsumerService.callMethod(request);
    }
}
