package com.kanlon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 启动类
 * @since 2019-04-09
 * @author zhangcanlong
 **/
@MapperScan("com.kanlon.mapper")
@SpringBootApplication
@EnableAsync
public class SpringBootQuartzDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootQuartzDemoApplication.class, args);
    }

}
