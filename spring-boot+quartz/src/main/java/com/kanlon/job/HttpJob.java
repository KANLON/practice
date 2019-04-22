package com.kanlon.job;

import com.kanlon.common.Constant;
import com.kanlon.model.CommonResponse;
import com.kanlon.model.QuartzResultModel;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * http协议的定时任务
 *
 * @author zhangcanlong
 * @since 2019-04-10
 **/
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class HttpJob extends QuartzJobBean {
    private  Logger logger = LoggerFactory.getLogger(HttpJob.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CommonJobService commonJobService;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        //初始调度结果
        QuartzResultModel model = commonJobService.startSchedule();
        Long oldTime = System.currentTimeMillis();
        try {
            Trigger trigger = context.getTrigger();
            logger.info("执行的任务名为："+trigger.getKey());
            JobDataMap data = trigger.getJobDataMap();
            String invokeParam = (String) data.get(Constant.INVOKE_PARAM_STR);
            model.setQuartzId((Long) data.get(Constant.QUARTZ_ID_STR));
            ResponseEntity<CommonResponse> entity;
            try {
                entity = restTemplate.getForEntity(invokeParam, CommonResponse.class);
            } catch (HttpClientErrorException e) {
                //40*，50* 错误
                logger.error(e.getLocalizedMessage(), e);
                commonJobService.addResult(oldTime,e.getLocalizedMessage(),model,logger);
                return;
            }
            if (entity.getStatusCode() != HttpStatus.OK) {
                StringBuffer buffer = new StringBuffer("请求\"" + invokeParam + "\"任务失败！" + "链接错误、请求方法或服务器错误：" + entity);
                commonJobService.addResult(oldTime,buffer.toString(),model,logger);
                return;
            }
            CommonResponse commonResponse = entity.getBody();
            //如果返回的数据为null
            if (commonResponse == null) {
                StringBuffer buffer = new StringBuffer("请求\"" + invokeParam + "\"任务失败！" + "链接返回的数据为null");
                commonJobService.addResult(oldTime,buffer.toString(), model,logger);
                return;
            }
            //如果数据返回不正确，即执行不正确
            if (commonResponse.code != 1) {
                StringBuffer buffer = new StringBuffer("请求\"" + invokeParam + "\"任务失败！" + "返回的错误信息为：" + commonResponse.getMessage() + "',错误信息数据为:" + commonResponse.getMessageData());
                commonJobService.addResult(oldTime,buffer.toString(), model,logger);
                return;
            }
            //如果任务执行成功
            StringBuffer buffer = new StringBuffer("请求\"" + invokeParam + "\"任务成功！" + "返回的信息为：" + commonResponse);
            model.setExecResult(1);
            commonJobService.addResult(oldTime,buffer.toString(), model,logger);
        }catch (Exception e){
            //其他错误情况
            logger.error(e.getLocalizedMessage(), e);
            commonJobService.addResult(oldTime,e.getLocalizedMessage(),model,logger);
        }
    }



}