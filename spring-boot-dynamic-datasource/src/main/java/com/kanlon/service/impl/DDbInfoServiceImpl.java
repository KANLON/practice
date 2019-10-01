package com.kanlon.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kanlon.mapper.DDbInfoMapper;
import com.kanlon.model.DDbInfo;
import com.kanlon.service.IDDbInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 张灿龙,2019-07-17 10:30。说明：数据库信息 服务实现类
 * </p>
 *
 * @author zhangcanlon
 * @since 2019-09-01
 */
@Service
public class DDbInfoServiceImpl extends ServiceImpl<DDbInfoMapper, DDbInfo> implements IDDbInfoService {

    /**
     * 存取全局的数据源
     **/
    private final Map<Long, DataSource> dataSourceMap = new ConcurrentHashMap<>(16);


    /**
     * 管理数据源信息
     *
     * @param dDbInfo 数据源信息
     **/
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public void addOrUpdateDataSource(DDbInfo dDbInfo) {
        if (dDbInfo.getId() == null) {
            this.save(dDbInfo);
            this.createDataSource(dDbInfo);
        } else {
            // 先建新的数据源，如果新的能连接通，则删除旧的
            DruidDataSource oldDataSource = (DruidDataSource) dataSourceMap.get(dDbInfo.getId());
            this.createDataSource(dDbInfo);
            if (oldDataSource != null) {
                oldDataSource.close();
            }
            dDbInfo.setCreated(null);
            this.updateById(dDbInfo);
        }
    }


    /**
     * 执行sql
     *
     * @param dbId 连接的数据源
     * @param sql  执行的sql
     * @return 执行结果
     **/
    @Override
    public List<Map<String, Object>> execSqlWithSelfDataSource(Long dbId, String sql) {
        DDbInfo dbInfo = this.getById(dbId);
        if (dbInfo == null) {
            throw new RuntimeException("数据库不存在！");
        }
        DataSource dataSource = dataSourceMap.get(dbId);
        if (dataSource == null || ((DruidDataSource) dataSource).isClosed()) {
            dataSource = this.createDataSource(dbInfo);
        }
        List<Map<String, Object>> resultData = new ArrayList<>();
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCnt = resultSet.getMetaData().getColumnCount();
            // 遍历resultSet，将数据放入到list集合中
            while (resultSet.next()) {
                Map<String, Object> map = new LinkedHashMap<>(16);
                for (int i = 1; i <= columnCnt; i++) {
                    map.put(metaData.getColumnLabel(i), resultSet.getObject(i));
                }
                resultData.add(map);
            }
        } catch (SQLException e) {
            log.error("执行sql错误", e);
            throw new RuntimeException("执行sql错误！" + e.getMessage());
        }
        return resultData;
    }

    /**
     * 根据数据库信息，创建数据源，如果数据源不存在的时候
     *
     * @param dDbInfo 数据库信息
     **/
    private DataSource createDataSource(DDbInfo dDbInfo) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(dDbInfo.getDbUsername());
        String url;
        if (dDbInfo.getDbType() == 1) {
            url = "jdbc:mysql://" + dDbInfo.getHost() + ":" + dDbInfo.getPort() + "/" + dDbInfo.getDatabaseName() +
                    "?useUnicode=true&characterEncoding=utf-8";
        } else {
            url = "jdbc:mysql://" + dDbInfo.getHost() + ":" + dDbInfo.getPort() + "/" + dDbInfo.getDatabaseName() +
                    "?useUnicode=true&characterEncoding=utf-8";
        }
        dataSource.setUrl(url);
        dataSource.setPassword(dDbInfo.getDbPassword());
        dataSource.setInitialSize(1);
        dataSource.setMinIdle(2);
        dataSource.setTestWhileIdle(true);
        dataSource.setMaxWait(2000);
        dataSource.setKeepAlive(true);
        dataSource.setBreakAfterAcquireFailure(true);
        dataSource.setConnectionErrorRetryAttempts(0);
        dataSource.setTimeBetweenConnectErrorMillis(1000);
        dataSource.setTimeBetweenEvictionRunsMillis(600000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setValidationQuery("select 1");
        dataSource.setRemoveAbandonedTimeout(800);
        dataSource.setName(String.valueOf(dDbInfo.getId()));
        // 测试数据源是否连接得通
        try (Connection connection = dataSource.getConnection()) {
            log.debug("建立数据源连接");
        } catch (SQLException e) {
            throw new RuntimeException("连接数据源失败！" + e.getMessage());
        }
        dataSourceMap.put(dDbInfo.getId(), dataSource);
        return dataSource;
    }

}
