package com.kanlon.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.kanlon.common.DateTimeFormat;

import java.util.Date;

/**
 * 调度任务的信息，连表查询的信息
 *
 * @author zhangcanlong
 * @since 2019/4/15 12:46
 **/
public class QuartzInfo {
    /**任务主键*/
    private Integer quartzId;
    /**任务名称*/
    private String jobName;
    /**任务分组*/
    private String jobGroup;
    /**任务负责人*/
    private String charge;
    /**任务负责人部门*/
    private String chargeDepartment;
    /**任务开始时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    /**corn表达式*/
    private String cronExpression;
    /**需要传递的参数*/
    private String invokeParam;
    /**需要传递的参数2*/
    private String invokeParam2;
    /**修改时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date mtime;
    /**描述*/
    private String description;
    /**下次执行时间,获取时，将long型时间转化为“yyyy-MM-dd HH:mm:ss”这种形式的字符串*/
    private String nextFireTime;
    /**任务状态*/
    private String triggerState;

    public Integer getQuartzId() {
        return quartzId;
    }

    public void setQuartzId(Integer quartzId) {
        this.quartzId = quartzId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getInvokeParam() {
        return invokeParam;
    }

    public void setInvokeParam(String invokeParam) {
        this.invokeParam = invokeParam;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNextFireTime() {
        String dateStr = DateTimeFormat.printIOS(new Date(Long.parseLong(this.nextFireTime)));
        return dateStr;
    }

    public void setNextFireTime(String nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public String getTriggerState() {
        return triggerState;
    }

    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getChargeDepartment() {
        return chargeDepartment;
    }

    public void setChargeDepartment(String chargeDepartment) {
        this.chargeDepartment = chargeDepartment;
    }

    public String getInvokeParam2() {
        return invokeParam2;
    }

    public void setInvokeParam2(String invokeParam2) {
        this.invokeParam2 = invokeParam2;
    }

    @Override
    public String toString() {
        return "QuartzInfo{" + "quartzId=" + quartzId + ", jobName='" + jobName + '\'' + ", jobGroup='" + jobGroup + '\'' + ", charge='" + charge + '\'' + ", chargeDepartment='" + chargeDepartment + '\'' + ", startTime='" + startTime + '\'' + ", cronExpression='" + cronExpression + '\'' + ", invokeParam='" + invokeParam + '\'' + ", invokeParam2='" + invokeParam2 + '\'' + ", mtime='" + mtime + '\'' + ", description='" + description + '\'' + ", nextFireTime='" + nextFireTime + '\'' + ", triggerState='" + triggerState + '\'' + '}';
    }
}
