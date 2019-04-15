package com.kanlon.service;

import com.kanlon.common.Constant;
import com.kanlon.common.DateTimeFormat;
import com.kanlon.exception.QuartzException;
import com.kanlon.model.AppQuartz;
import com.kanlon.model.CommonResponse;
import com.kanlon.model.ScheduleJob;
import com.kanlon.job.HttpJob;
import com.kanlon.job.ShellJob;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 任务的service 工具类
 *
 * @author zhangcanlong
 * @since 2019-04-10
 **/
@Service
public class JobUtil {

    @Autowired
    @Qualifier("scheduler")
    private Scheduler scheduler;

    private Logger logger = LoggerFactory.getLogger(JobUtil.class);

    /**
     * 新建一个任务,因为这里暂时不要很复杂的调度，所以现在的情况是，jobDetail和Trigger是绑定的，一个JobDetail对应一个Trigger
     */
    public void addJob(AppQuartz appQuartz) throws Exception {
        if (!CronExpression.isValidExpression(appQuartz.getCronExpression())) {
            throw new QuartzException("非法的cron 表达式");
        }
        JobDetail jobDetail = null;
        //构建job信息
        if (Constant.HTTP_STR.equals(appQuartz.getJobGroup())) {
            jobDetail = JobBuilder.newJob(HttpJob.class)
                    .withIdentity(appQuartz.getJobName(), appQuartz.getJobGroup())
                    .build();
        }
        if (Constant.SHELL_STR.equals(appQuartz.getJobGroup())) {
            jobDetail = JobBuilder.newJob(ShellJob.class)
                    .withIdentity(appQuartz.getJobName(), appQuartz.getJobGroup())
                    .withDescription(appQuartz.getDescription())
                    .build();
        }
        //表达式调度构建器(即任务执行的时间,不立即执行)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(appQuartz.getCronExpression()).withMisfireHandlingInstructionDoNothing();
        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(appQuartz.getJobName(), appQuartz.getJobGroup())
                .startAt(DateTimeFormat.parseIOS(appQuartz.getStartTime()))
                .withSchedule(scheduleBuilder)
                .withDescription(appQuartz.getDescription())
                .build();
        //传递参数
        if (!StringUtils.isEmpty(appQuartz.getInvokeParam())) {
            trigger.getJobDataMap().put(Constant.INVOKE_PARAM_STR, appQuartz.getInvokeParam());
            trigger.getJobDataMap().put(Constant.QUARTZ_ID_STR, appQuartz.getQuartzId());
        }
        //将触发器与任务绑定在一起
        scheduler.scheduleJob(jobDetail, trigger);
        logger.info("插入内置quartz成功");
    }

    /**
     * 修改任务
     * @param appQuartz 任务信息
     **/
    public void modifyJob(AppQuartz appQuartz) throws SchedulerException, ParseException {
        if (!CronExpression.isValidExpression(appQuartz.getCronExpression())) {
            throw new QuartzException("Illegal cron expression,corn表达式不正确");
        }
        TriggerKey triggerKey = TriggerKey.triggerKey(appQuartz.getJobName(), appQuartz.getJobGroup());
        JobKey jobKey = new JobKey(appQuartz.getJobName(), appQuartz.getJobGroup());
        if (scheduler.checkExists(jobKey) && scheduler.checkExists(triggerKey)) {
        } else {
            throw new QuartzException("job or trigger 不存在");
        }
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        //表达式调度构建器,不立即执行
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(appQuartz.getCronExpression()).withMisfireHandlingInstructionDoNothing();
        //按新的cronExpression表达式重新构建trigger
        trigger = trigger.getTriggerBuilder()
                .startAt(DateTimeFormat.parseIOS(appQuartz.getStartTime()))
                .withIdentity(triggerKey)
                .withDescription(appQuartz.getDescription())
                .withSchedule(scheduleBuilder).build();
        //修改参数
        if (!trigger.getJobDataMap().get(Constant.INVOKE_PARAM_STR).equals(appQuartz.getInvokeParam())) {
            trigger.getJobDataMap().put(Constant.INVOKE_PARAM_STR, appQuartz.getInvokeParam());
            trigger.getJobDataMap().put(Constant.QUARTZ_ID_STR, appQuartz.getQuartzId());
        }
        //按新的trigger重新设置job执行
        scheduler.rescheduleJob(triggerKey, trigger);
        logger.info("修改内置quartz信息成功");
    }

    /**
     * 删除某个任务
     * @param jobName 任务名称
     * @param groupName 组名称
     **/
    public void deleteJob(String jobName,String groupName) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, groupName);
        if (!scheduler.checkExists(jobKey)) {
            throw new QuartzException( "该任务不存在，不能删除");
        }
        scheduler.deleteJob(jobKey);
    }

    /**
     * 暂停某个任务
     * @param jobName 任务名
     * @param jobGroup 任务组名
     * @return
     **/
    public void pauseJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if(!scheduler.checkExists(jobKey)){
            throw new QuartzException("该任务不存在，不能暂停");
        }
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复某个任务
     * @param jobName 任务名
     * @param jobGroup 任务组名
     **/
    public void resumeJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if(!scheduler.checkExists(jobKey)){
            throw new QuartzException("该任务不存在，不能恢复");
        }
        scheduler.resumeJob(jobKey);
    }

    /**
     * 暂停所有任务
     **/
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 恢复所有任务
     **/
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    /**
     * 获取Job状态
     *
     * @param jobName 任务名
     * @param jobGroup 任务组名
     * @return 状态信息
     * @throws SchedulerException
     */
    public String getJobState(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);
        return scheduler.getTriggerState(triggerKey).name();
    }

    /**
     * 过期的获取全部任务
     * @return com.kanlon.model.CommonResponse
     **/
    @Deprecated
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