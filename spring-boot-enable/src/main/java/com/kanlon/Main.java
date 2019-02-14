package com.kanlon;

import com.kanlon.enable.EnableSelfBean;
import com.kanlon.entity.Role;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhangcanlong
 * @since 2019/2/14 11:40
 **/
@EnableSelfBean
@ComponentScan("com.kanlon")
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        System.out.println(context.getBean(SecurityProperties.User.class));
        System.out.println(context.getBean(Role.class));
    }
}