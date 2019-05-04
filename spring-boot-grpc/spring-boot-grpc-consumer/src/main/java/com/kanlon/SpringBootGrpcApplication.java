package com.kanlon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 启动类
 *
 * @author zhangcanlong
 * @since 2019-05-04
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class SpringBootGrpcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootGrpcApplication.class, args);
    }

}
