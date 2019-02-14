package com.kanlon;

import com.kanlon.enable.EnableSelfBean;
import com.kanlon.entity.Role;
import com.kanlon.utils.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@EnableSelfBean(packages = {"com.kanlon.entity", "com.kanlon.utils"})
public class SpringBootEnableApplication {

    @Autowired
    Role abc;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringBootEnableApplication.class, args);
        //打印出所有spring中注册的bean
        System.out.println("当前spring注册的bean类");
        String[] allBeans = context.getBeanDefinitionNames();
        for (String bean : allBeans) {
            System.out.println(bean);
        }
        System.out.println("已注册Role：" + context.getBean(Role.class));
        SpringBootEnableApplication application = context.getBean(SpringBootEnableApplication.class);
        System.out.println("Role的测试方法：" + application.abc.test());

        System.out.println(context.getBean(ClassUtils.class).test());
    }
}

