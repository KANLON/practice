package com.kanlon.job;

import com.kanlon.common.DateTimeFormat;
import com.kanlon.model.QuartzResultModel;
import com.kanlon.service.QuartzResultService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 公共的任务调度逻辑
 *
 * @author zhangcanlong
 * @since 2019/4/22 16:09
 **/
@Component
public class CommonJobService {

    @Autowired
    private QuartzResultService quartzResultService;

    /**
     * 开始进行服务调度，初始化调度结果
     * @return QuartzResultModel初始化调度结果
     **/
    public QuartzResultModel startSchedule(){
        QuartzResultModel model = new QuartzResultModel();
        Date date = new Date();
        model.setCtime(date);
        model.setStartTime(date);
        model.setDt(DateTimeFormat.printLocal(date));
        //设置调度成功
        model.setScheduleResult(1);
        return model;
    }
    
    /**
     * 添加执行结果到数据库
     **/
    public void addResult(long oldTime,String remark, QuartzResultModel model, Logger logger) {
        model.setExecTime(System.currentTimeMillis() - oldTime);
        model.setCompleteTime(new Date());
        model.setRemark(remark);
        if (model.getExecResult() == null) {
            model.setExecResult(0);
            logger.error(remark);
        }else{
            logger.info(remark);
        }
        if (model.getScheduleResult() == null) {
            model.setScheduleResult(0);
        }
        quartzResultService.addQuartzResult(model);
    }
    
    
}
