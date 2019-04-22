package com.kanlon.job;

import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 发送邮件的定时任务
 *
 * @author zhangcanlong
 * @since 2019/4/22 18:27
 **/
public class EmailJob  extends QuartzJobBean {


    @Override
    protected void executeInternal(JobExecutionContext context) {

    }
}
