package com.kanlon;

import com.kanlon.enable.EnableSelfBean;
import com.kanlon.entity.Role;
import com.kanlon.utils.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@EnableSelfBean(packages = "com.kanlon.entity")
public class SpringBootEnableApplication {

    @Autowired
    Role abc;

    public static void main(String[] args) {
        ConfigurableApplicationContext context =  SpringApplication.run(SpringBootEnableApplication.class, args);
        System.out.println(context.getBean(Role.class));
        //System.out.println(context.getBean(ClassUtils.class));
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
        String[] allBeans = context.getBeanDefinitionNames();
        for(String bean:allBeans){
            System.out.println(bean);
        }
        Role role = context.getBean(Role.class);
        System.out.println(role.test());

        SpringBootEnableApplication application = context.getBean(SpringBootEnableApplication.class);
        System.out.println(application.abc.test());

    }

}

