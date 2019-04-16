package com.kanlon.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * 时间格式配置
 * @author zhangcanlong
 * @since 2019-04-16
 **/
@Configuration
@EnableCaching
public class CommonConfigurer {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }
}
