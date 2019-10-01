package com.kanlon.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 执行sql 的请求参数
 *
 * @author zhangcanlong
 * @since 2019/10/1 16:00
 **/
@Data
public class ExecSqlRequest {

    /**
     * 执行的数据库的id
     **/
    @NotNull
    @Min(1)
    private Long dbId;

    /**
     * 执行的sql
     **/
    @NotNull
    private String sql;
}
