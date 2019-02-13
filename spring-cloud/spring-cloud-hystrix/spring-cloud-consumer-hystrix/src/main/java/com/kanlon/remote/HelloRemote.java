package com.kanlon.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 远程调用服务提供类的hello
 *
 * @author zhangcanlong
 * @since 2019/2/13 16:44
 **/
@FeignClient(name = "spring-cloud-producer",fallback = HelloRemoteHystrix.class)
public interface HelloRemote {

    @RequestMapping("/hello")
    public String index(@RequestParam(value = "name") String name);
}
