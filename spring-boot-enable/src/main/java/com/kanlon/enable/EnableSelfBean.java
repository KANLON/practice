package com.kanlon.enable;

import com.kanlon.config.SelfEnableAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 自定义注解类，将某个包下的所有类自动加载到spring 容器中，不管有没有注解，并打印出
 * @author zhangcanlong
 * @since 2019/2/14 10:42
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SelfEnableAutoConfig.class)
public @interface EnableSelfBean {
    //传入包名
    String[] packages() default "";
}
