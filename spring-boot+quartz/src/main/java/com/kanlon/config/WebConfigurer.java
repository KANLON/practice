package com.kanlon.config;

import com.kanlon.common.DateTimeFormat;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Date;

/**
 * spring mvc过滤器和日期格式配置
 *
 * @author zhangcanlong
 * @since 2019-04-16
 **/
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(Date.class, new DateTimeFormat());
    }

}
