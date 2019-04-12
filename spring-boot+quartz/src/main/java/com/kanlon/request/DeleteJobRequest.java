package com.kanlon.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 删除任务调度的请求参数
 *
 * @author zhangcanlong
 * @since 2019/4/12 15:39
 **/
public class DeleteJobRequest {
    @NotEmpty
    @NotNull
    private Integer[] quartzIds;

    public Integer[] getQuartzIds() {
        return quartzIds;
    }

    public void setQuartzIds(Integer[] quartzIds) {
        this.quartzIds = quartzIds;
    }
}
