package com.kanlon.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kanlon.mapper.DDbInfoMapper;
import com.kanlon.model.DDbInfo;
import com.kanlon.service.IDDbInfoService;
import com.kanlon.util.AesUtil;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${db.password-key}")
    private String dbPasswordKey;

    /**
     * 管理数据源信息
     *
     * @param dDbInfo 数据源信息
     **/
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public void addOrUpdateDataSource(final DDbInfo dDbInfo) {
        final String encryptPassword = AesUtil.aesPKCS5PaddingEncrypt(dDbInfo.getDbPassword(), this.dbPasswordKey);
        dDbInfo.setDbPassword(encryptPassword);
        if (dDbInfo.getId() == null) {
            this.save(dDbInfo);
            this.createDataSource(dDbInfo);
        } else {
            // 先建新的数据源，如果新的能连接通，则删除旧的
            final DruidDataSource oldDataSource = (DruidDataSource) this.dataSourceMap.get(dDbInfo.getId());
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
    public List<Map<String, Object>> execSqlWithSelfDataSource(final Long dbId, final String sql) {
        final DDbInfo dbInfo = this.getById(dbId);
        if (dbInfo == null) {
            throw new RuntimeException("数据库不存在！");
        }
        DataSource dataSource = this.dataSourceMap.get(dbId);
        if (dataSource == null || ((DruidDataSource) dataSource).isClosed()) {
            dataSource = this.createDataSource(dbInfo);
        }
        final List<Map<String, Object>> resultData = new ArrayList<>();
        try (final Connection connection = dataSource.getConnection(); final Statement statement =
                connection.createStatement(); final ResultSet resultSet = statement.executeQuery(sql)) {
            final ResultSetMetaData metaData = resultSet.getMetaData();
            final int columnCnt = resultSet.getMetaData().getColumnCount();
            // 遍历resultSet，将数据放入到list集合中
            while (resultSet.next()) {
                final Map<String, Object> map = new LinkedHashMap<>(16);
                for (int i = 1; i <= columnCnt; i++) {
                    map.put(metaData.getColumnLabel(i), resultSet.getObject(i));
                }
                resultData.add(map);
            }
        } catch (final SQLException e) {
            this.log.error("执行sql错误", e);
            throw new RuntimeException("执行sql错误！" + e.getMessage());
        }
        return resultData;
    }

    /**
     * 根据数据库信息，创建数据源，如果数据源不存在的时候
     *
     * @param dDbInfo 数据库信息,其中的密码都是加密了的
     **/
    private DataSource createDataSource(final DDbInfo dDbInfo) {
        final String originPassword = AesUtil.aesPKCS5PaddingDecrypt(dDbInfo.getDbPassword(), this.dbPasswordKey);
        final DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(dDbInfo.getDbUsername());
        final String url;
        if (dDbInfo.getDbType() == 1) {
            url = "jdbc:mysql://" + dDbInfo.getHost() + ":" + dDbInfo.getPort() + "/" + dDbInfo.getDatabaseName() +
                    "?useUnicode=true&characterEncoding=utf-8";
        } else {
            url = "jdbc:mysql://" + dDbInfo.getHost() + ":" + dDbInfo.getPort() + "/" + dDbInfo.getDatabaseName() +
                    "?useUnicode=true&characterEncoding=utf-8";
        }
        dataSource.setUrl(url);
        dataSource.setPassword(originPassword);
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
        try (final Connection connection = dataSource.getConnection()) {
            this.log.debug("建立数据源连接");
        } catch (final SQLException e) {
            throw new RuntimeException("连接数据源失败！" + e.getMessage());
        }
        this.dataSourceMap.put(dDbInfo.getId(), dataSource);
        return dataSource;
    }

}
