package com.kanlon.model;

import javax.validation.constraints.NotNull;

/**
 * 任务调度请求参数
 *
 * @author zhangcanlong
 * @since 2019/5/4 17:44
 **/
public class ConsumerRequest {
    /**
     * 传递参数2
     */
    @NotNull
    private String param1;
    /**
     * 传递参数2
     */
    private String param2;
    /**
     * 调用方法的类型，是调用http方法，还是shell方法
     */
    @NotNull
    private String type;
    /**
     * 方法提供方的应用名
     */
    @NotNull
    private String providerName;

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
}
