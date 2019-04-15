package com.kanlon.job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 执行shell的定时任务
 * @author zhangcanlong
 * @since 2019-04-10
 **/
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class ShellJob implements Job {
    private Logger logger = LoggerFactory.getLogger(ShellJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap data=context.getTrigger().getJobDataMap();
        String invokeParam =(String) data.get("invokeParam");
            //在这里实现业务逻辑
            //这里只是测试简单打印出参数名
        System.out.println("任务2，传递的参数为："+invokeParam);
        logger.info("任务2，传递的参数为："+invokeParam);
        }
}