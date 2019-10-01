package com.kanlon.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 张灿龙,2019-07-17 10:30。说明：数据库信息
 * </p>
 *
 * @author zhangcanlon
 * @since 2019-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DDbInfo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 数据库的名字，别名
     */
    @NotNull
    private String name;

    /**
     * 数据库的描述
     */
    private String description;

    /**
     * 主机地址或域名
     */
    @NotNull
    private String host;

    /**
     * 主机的端口
     */
    @NotNull
    private Integer port;

    /**
     * 用户名
     */
    @NotNull
    private String dbUsername;

    /**
     * 密码
     */
    private String dbPassword;

    /**
     * 要连接的数据库名
     */
    private String databaseName;

    /**
     * 数据库类型,目前只有一种，1为mysql
     */
    @NotNull
    private Integer dbType;

    /**
     * 创建者dw用户名
     */
    @JsonIgnore
    private String username;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date created =new Date();

    /**
     * 修改时间
     */
    @JsonIgnore
    private Date updated = new Date();

    /**
     * 是否删除 1删除 0不删除
     */
    @TableLogic
    @JsonIgnore
    private Integer isDeleted;


}
