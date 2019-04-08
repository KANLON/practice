package com.kanlon.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 线程池配置类
 *
 * @author zhangcanlong
 * @since 2019/3/31 15:43
 **/
@Configuration
@EnableAsync
public class TaskAsyncConfigurer implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        threadPool.setCorePoolSize(10);
        threadPool.setMaxPoolSize(100);
        threadPool.setQueueCapacity(10);
        threadPool.setWaitForTasksToCompleteOnShutdown(true);
        //设置线程等待结束时间，默认为0
        threadPool.setAwaitTerminationSeconds(60);
        //初始化线程
        threadPool.initialize();
        return threadPool;
    }
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }

}
