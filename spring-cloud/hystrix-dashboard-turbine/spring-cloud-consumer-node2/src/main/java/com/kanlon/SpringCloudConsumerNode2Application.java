package com.kanlon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SpringCloudConsumerNode2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConsumerNode2Application.class, args);
    }

}

