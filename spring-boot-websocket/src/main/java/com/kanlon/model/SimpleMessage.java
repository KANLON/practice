package com.kanlon.model;

import java.util.Date;

/**
 * 发布订阅的的消息的实体类
 *
 * @author zhangcanlong
 * @since 2019-06-02
 **/
public class SimpleMessage {
    /**
     * 发布者
     **/
    private String publisher;

    /**
     * 发布内容
     **/
    private String content;

    /**
     * 发布时间
     **/
    private Date createTime;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
