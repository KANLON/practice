package com.kanlon.controller;

import com.kanlon.model.AppQuartz;
import com.kanlon.model.ReturnMsg;
import com.kanlon.service.AppQuartzService;
import com.kanlon.service.JobUtil;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
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
    private SchedulerFactoryBean schedulerFactoryBean;

    
    private Logger logger = LoggerFactory.getLogger(JobController.class);

    /**
     * 添加一个job
     * @param appQuartz 任务信息
     * @return com.kanlon.model.ReturnMsg
     **/
    @RequestMapping(value="/addJob",method= RequestMethod.POST)
    public ReturnMsg addjob(@RequestBody AppQuartz appQuartz) throws Exception {
        appQuartzService.insertAppQuartzSer(appQuartz);        
        jobUtil.addJob(appQuartz);
        return new ReturnMsg("200","添加成功");
    }
    
    /**
     * 暂停job
     * @param quartzIds id
     * @return com.kanlon.model.ReturnMsg
     **/
    @RequestMapping(value="/pauseJob",method=RequestMethod.POST)
    public ReturnMsg pausejob(@RequestBody Integer[]quartzIds) throws Exception {
        AppQuartz appQuartz=null;            
        if(quartzIds.length>0){
            for(Integer quartzId:quartzIds) {
                appQuartz=appQuartzService.selectAppQuartzByIdSer(quartzId);
                jobUtil.pauseJob(appQuartz.getJobName(), appQuartz.getJobGroup());                        
            }
            return new ReturnMsg("200","success pauseJob");    
        }else {
            return new ReturnMsg("404","fail pauseJob");    
        }                                                                
    }
    
    /**
     * 恢复job
     * @param quartzIds id
     * @return com.kanlon.model.ReturnMsg
     **/
    @RequestMapping(value="/resumeJob",method=RequestMethod.POST)
    public ReturnMsg resumejob(@RequestBody Integer[]quartzIds) throws Exception {    
        AppQuartz appQuartz=null;
        if(quartzIds.length>0) {
            for(Integer quartzId:quartzIds) {
                appQuartz=appQuartzService.selectAppQuartzByIdSer(quartzId);
                jobUtil.resumeJob(appQuartz.getJobName(), appQuartz.getJobGroup());                
            }
            return new ReturnMsg("200","success resumeJob");
        }else {
            return new ReturnMsg("404","fail resumeJob");
        }            
    } 
        
    
    /**
     * 删除job
     * @param quartzIds id
     * @return com.kanlon.model.ReturnMsg
     **/
    @RequestMapping(value="/deletJob",method=RequestMethod.POST)
    public ReturnMsg deletjob(@RequestBody Integer[]quartzIds) throws Exception {
        AppQuartz appQuartz=null;
        for(Integer quartzId:quartzIds) {
            appQuartz=appQuartzService.selectAppQuartzByIdSer(quartzId);
            String ret=jobUtil.deleteJob(appQuartz);
            if("success".equals(ret)) {
                appQuartzService.deleteAppQuartzByIdSer(quartzId);
            }
        }
        return new ReturnMsg("200","success deleteJob");    
    }
        
    /**
     * 修改
     * @param appQuartz
     * @return com.kanlon.model.ReturnMsg
     **/
    @RequestMapping(value="/updateJob",method=RequestMethod.POST)
    public ReturnMsg  modifyJob(@RequestBody AppQuartz appQuartz) throws Exception {
        String ret= jobUtil.modifyJob(appQuartz);            
        if("success".equals(ret)) {            
            appQuartzService.updateAppQuartzSer(appQuartz);
            return new ReturnMsg("200","success updateJob",ret);
        }else {
            return new ReturnMsg("404","fail updateJob",ret);
        }                
    }
    
    /**
     * 暂停所有
     * @return com.kanlon.model.ReturnMsg
     **/
    @RequestMapping(value="/pauseAll",method=RequestMethod.GET)
    public ReturnMsg pauseAllJob() throws Exception {
        jobUtil.pauseAllJob();
        return new ReturnMsg("200","success pauseAll");
    }
    
    /**
     * 恢复所有
     * @return com.kanlon.model.ReturnMsg
     **/
    @RequestMapping(value="/repauseAll",method=RequestMethod.GET)
    public ReturnMsg repauseAllJob() throws Exception {
        jobUtil.resumeAllJob();
        return new ReturnMsg("200","success repauseAll");
    }

    /**
     * 获取所有任务
     * @return com.kanlon.model.ReturnMsg
     **/
    @GetMapping("/tasks")
    public ReturnMsg getAllTask() throws SchedulerException {
        List<String> allJobNames = new ArrayList<>();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        for(String groupName:scheduler.getJobGroupNames()){
            for(JobKey jobKey: scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))){
                String jobName = jobKey.getName();
                String jobGroup = jobKey.getGroup();
                allJobNames.add(jobName+"-"+jobGroup);
                //get job trigger
                List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                Date nextFireTime = triggers.get(0).getNextFireTime();
                System.out.println("[jobName] : " + jobName + " [groupName] : "
                        + jobGroup + " - " + nextFireTime);
            }
        }

        List<ScheduleJob>  list = getAllJob();

        System.out.println(list);
        return ReturnMsg.successReturn("200",allJobNames);
    }


    /** * 获取所有计划中的任务列表 * * @return * @throws SchedulerException */
    public List<ScheduleJob> getAllJob() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
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
        return jobList;
    }

    
}

class ScheduleJob{
    private String name;
    private String group;
    private String description;
    private String cron;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}