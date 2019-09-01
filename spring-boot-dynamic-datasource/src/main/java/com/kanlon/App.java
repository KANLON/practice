package com.kanlon;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 启动类
 *
 * @author zhangcanlong
 **/
@SpringBootApplication
@MapperScan("com.kanlon.mapper")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置最大分页限制为不限制
        paginationInterceptor.setLimit(0);
        return paginationInterceptor;
    }
}
