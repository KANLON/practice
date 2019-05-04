package com.kanlon.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * tb_app_quartz表的实体类
 *
 * @author zhangcanlong
 * @since 2019-04-09
 **/
public class AppQuartz {
    /**
     * 任务主键
     */
    private Long quartzId;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 任务组名
     */
    private String jobGroup;
    /**
     * 负责人名称
     */
    private String charge;
    /**
     * 负责人部门
     */
    @JsonProperty("chargeDepartment")
    private String chargeDepartment;
    /**
     * 任务开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    /**
     * corn表达式
     */
    private String cronExpression;
    /**
     * 需要传递的参数
     */
    private String invokeParam;
    /**
     * 需要传递的参数2，目前参数2只用于邮件发送时指定发送标题和正文内容，并以#分割
     */
    private String invokeParam2;
    /**
     * 描述
     */
    private String description;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date ctime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date mtime;

    public Long getQuartzId() {
        return quartzId;
    }

    public void setQuartzId(Long quartzId) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public String getInvokeParam2() {
        return invokeParam2;
    }

    public void setInvokeParam2(String invokeParam2) {
        this.invokeParam2 = invokeParam2;
    }

    @Override
    public String toString() {
        return "AppQuartz{" + "quartzId=" + quartzId + ", jobName='" + jobName + '\'' + ", jobGroup='" + jobGroup + '\'' + ", charge='" + charge + '\'' + ", chargeDepartment='" + chargeDepartment + '\'' + ", startTime='" + startTime + '\'' + ", cronExpression='" + cronExpression + '\'' + ", invokeParam='" + invokeParam + '\'' + ", invokeParam2='" + invokeParam2 + '\'' + ", description='" + description + '\'' + ", ctime='" + ctime + '\'' + ", mtime='" + mtime + '\'' + '}';
    }
}
