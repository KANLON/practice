package com.kanlon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kanlon.model.DDbInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 张灿龙,2019-07-17 10:30。说明：数据库信息 服务类
 * </p>
 *
 * @author zhangcanlon
 * @since 2019-09-01
 */
public interface IDDbInfoService extends IService<DDbInfo> {

    /**
     * 增加或更新数据源信息
     *
     * @param dDbInfo 数据源信息
     **/
    void addOrUpdateDataSource(DDbInfo dDbInfo);

    /**
     * 执行sql
     *
     * @param dbId 连接的数据源
     * @param sql  执行的sql
     * @return 执行结果
     **/
    List<Map<String,Object>> execSqlWithSelfDataSource(Long dbId,String sql);
}
