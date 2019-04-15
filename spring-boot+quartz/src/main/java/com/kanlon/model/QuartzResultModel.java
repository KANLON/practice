package com.kanlon.model;

/**
 * 任务执行日志结果表操作的mapper
 *
 * @author zhangcanlong
 * @since 2019/4/15 20:56
 **/
public class QuartzResultModel {
    /**任务id*/
    private Long quartzId;
    /**调度开始时间*/
    private String startTime;
    /**调度结果*/
    private Integer scheduleResult;
    /**调度任务执行结果*/
    private Integer execResult;
    /**执行时间,毫秒值*/
    private Long execTime;
    /**调度/任务执行完成时间*/
    private String completeTime;
    /**备注*/
    private String remark;
    /**创建时间*/
    private String ctime;

    public Long getQuartzId() {
        return quartzId;
    }

    public void setQuartzId(Long quartzId) {
        this.quartzId = quartzId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getScheduleResult() {
        return scheduleResult;
    }

    public void setScheduleResult(Integer scheduleResult) {
        this.scheduleResult = scheduleResult;
    }

    public Integer getExecResult() {
        return execResult;
    }

    public void setExecResult(Integer execResult) {
        this.execResult = execResult;
    }

    public Long getExecTime() {
        return execTime;
    }

    public void setExecTime(Long execTime) {
        this.execTime = execTime;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }
}
