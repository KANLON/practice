package com.kanlon.model;

import org.springframework.hateoas.ResourceSupport;

/**
 * 日志记录表对应的实体类（用户测试百万数据查询优化）
 *
 * @author zhangcanlong
 * @since 2019/4/2 20:52
 **/

public class LogPO extends ResourceSupport {
    /**日期*/
    private String dt;
    /**其他1*/
    private String logtype;
    /**链接*/
    private String logurl;
    /**ip地址*/
    private String logip;
    /**其他1*/
    private String logdz;
    /**其他1*/
    private String ladduser;
    /**其他1*/
    private String lfadduser;
    /**其他1*/
    private String laddtime;
    /**页面名称*/
    private String htmlname;
    /**创建时间*/
    private String ctime;
    /**修改时间*/
    private String mtime;

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getLogtype() {
        return logtype;
    }

    public void setLogtype(String logtype) {
        this.logtype = logtype;
    }

    public String getLogurl() {
        return logurl;
    }

    public void setLogurl(String logurl) {
        this.logurl = logurl;
    }

    public String getLogip() {
        return logip;
    }

    public void setLogip(String logip) {
        this.logip = logip;
    }

    public String getLogdz() {
        return logdz;
    }

    public void setLogdz(String logdz) {
        this.logdz = logdz;
    }

    public String getLadduser() {
        return ladduser;
    }

    public void setLadduser(String ladduser) {
        this.ladduser = ladduser;
    }

    public String getLfadduser() {
        return lfadduser;
    }

    public void setLfadduser(String lfadduser) {
        this.lfadduser = lfadduser;
    }

    public String getLaddtime() {
        return laddtime;
    }

    public void setLaddtime(String laddtime) {
        this.laddtime = laddtime;
    }

    public String getHtmlname() {
        return htmlname;
    }

    public void setHtmlname(String htmlname) {
        this.htmlname = htmlname;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }
}
