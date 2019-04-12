package com.kanlon.model;

/**
 * tb_app_quartz表的实体类
 * @author zhangcanlong
 * @since 2019-04-09
 **/
public class AppQuartz {
    /**任务主键*/
    private Integer quartzId;
    /**任务名称*/
    private String jobName;
    /**任务分组*/
    private String jobGroup;
    /**任务开始时间*/
    private String startTime;
    /**corn表达式*/
    private String cronExpression;
    /**需要传递的参数*/
    private String invokeParam;
    /**创建时间*/
    private String ctime;

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
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

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }
}