package com.kanlon.task;

import com.kanlon.controller.JobController;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * 任务2，的具体实现，可以传递参数
 * @author zhangcanlong
 * @since 2019-04-10
 **/
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class JobOne implements Job {
    private Logger logger = LoggerFactory.getLogger(JobOne.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap data=context.getTrigger().getJobDataMap();
        String invokeParam =(String) data.get("invokeParam");
        //在这里实现业务逻辑
        //这里只是测试简单打印出参数名
        System.out.println("任务1，传递的参数为："+invokeParam);
        logger.info("任务1，传递的参数为："+invokeParam);
        }
}