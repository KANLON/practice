package com.linshen.grpcspringcloudconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GrpcClientController {

    @Autowired
    private GrpcClientService grpcClientService;

    @RequestMapping("/")
    public String printMessage(@RequestParam(defaultValue = "参数1") String param1,
            @RequestParam(defaultValue = "参数2")String param2) {
        return grpcClientService.sendMessage(param1,param2);
    }
}
