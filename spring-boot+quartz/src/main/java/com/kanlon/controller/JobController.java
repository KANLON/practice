package com.kanlon.controller;

import com.kanlon.model.AppQuartz;
import com.kanlon.model.CommonResponse;
import com.kanlon.model.ScheduleJob;
import com.kanlon.service.AppQuartzService;
import com.kanlon.service.JobUtil;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * job的controller
 * @author zhangcanlong
 * @since 2019-04-10
 **/
@RequestMapping("/")
@RestController
public class JobController {
    @Autowired
    private JobUtil jobUtil;

    @Autowired
    private AppQuartzService appQuartzService;

    @Autowired
    private Scheduler scheduler;

    
    private Logger logger = LoggerFactory.getLogger(JobController.class);

    /**
     * 添加一个job
     * @param appQuartz 任务信息
     * @return com.kanlon.model.ReturnMsg
     **/
    @PostMapping(value="/addJob")
    public CommonResponse addjob(@RequestBody AppQuartz appQuartz) throws Exception {
        jobUtil.addJob(appQuartz);
        appQuartzService.insertAppQuartzSer(appQuartz);
        return CommonResponse.succeedResult();
    }
    
    /**
     * 暂停job
     * @param quartzIds id
     * @return com.kanlon.model.ReturnMsg
     **/
    @PostMapping(value="/pauseJob")
    public CommonResponse pauseJob(@NotEmpty @RequestBody Integer[] quartzIds) throws Exception {
        AppQuartz appQuartz=null;            
            for(Integer quartzId:quartzIds) {
                appQuartz=appQuartzService.selectAppQuartzByIdSer(quartzId);
                jobUtil.pauseJob(appQuartz.getJobName(), appQuartz.getJobGroup());                        
            }
            return CommonResponse.succeedResult();
    }
    
    /**
     * 恢复job
     * @param quartzIds id
     * @return com.kanlon.model.ReturnMsg
     **/
    @PostMapping(value="/resumeJob")
    public CommonResponse resumejob(@NotEmpty @RequestBody Integer[] quartzIds) throws Exception {
        AppQuartz appQuartz=null;
            for(Integer quartzId:quartzIds) {
                appQuartz=appQuartzService.selectAppQuartzByIdSer(quartzId);
                jobUtil.resumeJob(appQuartz.getJobName(), appQuartz.getJobGroup());                
            }
            return CommonResponse.succeedResult();
    }
        
    
    /**
     * 删除job
     * @param quartzIds 请求参数id
     * @return com.kanlon.model.ReturnMsg
     **/
    @PostMapping(value="/deleteJob")
    public CommonResponse deleteJob(@RequestBody Integer[]  quartzIds) throws Exception {
        AppQuartz appQuartz=null;
        for(Integer quartzId : quartzIds) {
            appQuartz=appQuartzService.selectAppQuartzByIdSer(quartzId);
            if(appQuartz==null){
                throw new RuntimeException("该id不存在不能删除");
            }
            String ret=jobUtil.deleteJob(appQuartz);
            if("success".equals(ret)) {
                appQuartzService.deleteAppQuartzByIdSer(quartzId);
            }
        }
        return CommonResponse.succeedResult();
    }
        
    /**
     * 修改
     * @param appQuartz 新的任务信息
     * @return com.kanlon.model.ReturnMsg
     **/
    @PostMapping(value="/updateJob")
    public CommonResponse  modifyJob(@RequestBody AppQuartz appQuartz) throws Exception {
        String ret= jobUtil.modifyJob(appQuartz);            
        if("success".equals(ret)) {            
            appQuartzService.updateAppQuartzSer(appQuartz);
            return CommonResponse.succeedResult("200",ret);
        }else {
            return CommonResponse.failedResult("找不到该任务");
        }                
    }
    
    /**
     * 暂停所有
     * @return com.kanlon.model.ReturnMsg
     **/
    @GetMapping(value="/pauseAll")
    public CommonResponse pauseAllJob() throws Exception {
        jobUtil.pauseAllJob();
        return CommonResponse.succeedResult();
    }
    
    /**
     * 恢复所有
     * @return com.kanlon.model.ReturnMsg
     **/
    @RequestMapping(value="/repauseAll",method=RequestMethod.GET)
    public CommonResponse repauseAllJob() throws Exception {
        jobUtil.resumeAllJob();
        return CommonResponse.succeedResult();
    }

    /**
     * 从自己的数据库表获取任务，包含任务id
     * @return com.kanlon.model.CommonResponse
     **/
    @GetMapping("/allJobs")
    public CommonResponse getAllTaskFromMyTable(){
        return CommonResponse.succeedResult(appQuartzService.getAllTaskFromMyTable());
    }

    /**
     * 获取所有任务
     * @return com.kanlon.model.ReturnMsg
     **/
    @GetMapping("/tasks")
    public CommonResponse getAllTask() throws SchedulerException {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<ScheduleJob> jobList = new ArrayList<>();
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                ScheduleJob job = new ScheduleJob();
                job.setName(jobKey.getName());
                job.setGroup(jobKey.getGroup());
                job.setDescription("触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCron(cronExpression);
                }
                jobList.add(job);
            }
        }
        return CommonResponse.succeedResult(jobList);
    }

}