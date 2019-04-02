package com.kanlon.model;

import javax.validation.constraints.NotNull;

/**
 * 日志查询请求参数
 *
 * @author zhangcanlong
 * @since 2019/4/2 21:03
 **/
public class LogRequest {
    /**页面名称*/
    @NotNull
    private String htmlname;
    /**链接*/
    @NotNull
    private String logurl;
    /**时间*/
    @NotNull
    private String dt;
    /**dt的时间+2*/
    private String dt2;
    /**dt的时间+4*/
    private String dt4;
    /**dt的时间+6*/
    private String dt6;

    public String getHtmlname() {
        return htmlname;
    }

    public void setHtmlname(String htmlname) {
        this.htmlname = htmlname;
    }

    public String getLogurl() {
        return logurl;
    }

    public void setLogurl(String logurl) {
        this.logurl = logurl;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getDt2() {
        return dt2;
    }

    public void setDt2(String dt2) {
        this.dt2 = dt2;
    }

    public String getDt4() {
        return dt4;
    }

    public void setDt4(String dt4) {
        this.dt4 = dt4;
    }

    public String getDt6() {
        return dt6;
    }

    public void setDt6(String dt6) {
        this.dt6 = dt6;
    }
}
