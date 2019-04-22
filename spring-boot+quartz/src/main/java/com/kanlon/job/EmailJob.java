package com.kanlon.job;

import com.kanlon.common.Constant;
import com.kanlon.common.MailUtil;
import com.kanlon.exception.QuartzException;
import com.kanlon.model.QuartzResultModel;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 发送邮件的定时任务
 *
 * @author zhangcanlong
 * @since 2019/4/22 18:27
 **/
public class EmailJob  extends QuartzJobBean {

    Logger logger = LoggerFactory.getLogger(EmailJob.class);

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private CommonJobService commonJobService;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        //初始调度结果
        QuartzResultModel model = commonJobService.startSchedule();
        Long oldTime = System.currentTimeMillis();
        //存放执行结果日志
        StringBuffer resultLog = new StringBuffer();
        try {
            logger.info("执行的任务名为：" + context.getTrigger().getKey());
            JobDataMap data = context.getTrigger().getJobDataMap();
            model.setQuartzId((Long) data.get(Constant.QUARTZ_ID_STR));
            //第一个参数的为要发送到目的邮箱
            String to = (String) data.get(Constant.INVOKE_PARAM_STR);
            String[] invokeParam2 = ((String) data.get(Constant.INVOKE_PARAM2_STR)).split("#");
            //以#为分割，第一个#之前的内容为subject,即标题
            String subject=invokeParam2[0];
            String content=invokeParam2[1];
            mailUtil.sendHtmlMail(to,subject,content);
            model.setExecResult(1);
        }catch (Exception e){
            logger.error("定时发送邮件错误！",e);
            resultLog.append(e.getLocalizedMessage());
        }finally {
            commonJobService.addResult(oldTime, resultLog.toString(), model, logger);
        }
    }
}
