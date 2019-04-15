package com.kanlon.job;

import com.kanlon.common.Constant;
import com.kanlon.common.DateTimeFormat;
import com.kanlon.model.CommonResponse;
import com.kanlon.model.QuartzResultModel;
import com.kanlon.service.QuartzResultService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * http协议的定时任务
 *
 * @author zhangcanlong
 * @since 2019-04-10
 **/
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class HttpJob implements Job {
    private Logger logger = LoggerFactory.getLogger(HttpJob.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private QuartzResultService quartzResultService;

    @Override
    public void execute(JobExecutionContext context) {
        //设置执行结果
        QuartzResultModel model = new QuartzResultModel();
        model.setCtime(DateTimeFormat.printIOS(new Date()));
        Long oldTime = System.currentTimeMillis();
        try {
            JobDataMap data = context.getTrigger().getJobDataMap();
            String invokeParam = (String) data.get(Constant.INVOKE_PARAM_STR);
            model.setQuartzId((Long) data.get(Constant.QUARTZ_ID_STR));
            ResponseEntity<CommonResponse> entity;
            try {
                entity = restTemplate.getForEntity(invokeParam, CommonResponse.class);
            } catch (HttpClientErrorException e) {
                //40*，50* 错误
                logger.error(e.getLocalizedMessage(), e);
                addResult(oldTime,e.getLocalizedMessage(),model);
                return;
            }
            if (entity.getStatusCode() != HttpStatus.OK) {
                StringBuffer buffer = new StringBuffer("请求\"" + invokeParam + "\"任务失败！" + "链接错误、请求方法或服务器错误：" + entity);
                addResult(oldTime,buffer.toString(),model);
                return;
            }
            CommonResponse commonResponse = entity.getBody();
            //如果返回的数据为null
            if (commonResponse == null) {
                StringBuffer buffer = new StringBuffer("请求\"" + invokeParam + "\"任务失败！" + "链接返回的数据为null");
                addResult(oldTime,buffer.toString(), model);
                return;
            }
            //如果数据返回不正确，即执行不正确
            if (commonResponse.code != 1) {
                StringBuffer buffer = new StringBuffer("请求\"" + invokeParam + "\"任务失败！" + "返回的错误信息为：" + commonResponse.getMessage() + "',错误信息数据为:" + commonResponse.getMessageData());
                addResult(oldTime,buffer.toString(), model);
                return;
            }
            //如果任务执行成功
            StringBuffer buffer = new StringBuffer("请求\"" + invokeParam + "\"任务成功！" + "返回的信息为：" + commonResponse);
            model.setExecResult(1);
            addResult(oldTime,buffer.toString(), model);
        }catch (Exception e){
            //其他错误情况
            logger.error(e.getLocalizedMessage(), e);
            addResult(oldTime,e.getLocalizedMessage(),model);
        }
    }

    /**
     * 添加执行结果到数据库
     **/
    private void addResult(long oldTime,String remark, QuartzResultModel model) {
        logger.info(remark);
        model.setExecTime(System.currentTimeMillis() - oldTime);
        model.setCompleteTime(DateTimeFormat.printIOS(new Date()));
        model.setRemark(remark);
        if (model.getExecResult() == null) {
            model.setExecResult(0);
        }
        if (model.getScheduleResult() == null) {
            model.setScheduleResult(0);
        }
        quartzResultService.addQuartzResult(model);
    }

}