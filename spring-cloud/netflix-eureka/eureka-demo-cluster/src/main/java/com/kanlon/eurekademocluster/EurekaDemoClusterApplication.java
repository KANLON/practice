package com.kanlon.eurekademocluster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaDemoClusterApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaDemoClusterApplication.class, args);
    }

}

