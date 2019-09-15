package com.kanlon.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.kanlon.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * 数据源操作
 *
 * @author zhangcanlong
 * @since 2019-09-14
 **/
@RestController
@RequestMapping("datasource")
public class DataSourceController {


    @Autowired
    private DynamicRoutingDataSource dynamicRoutingDataSource;

    /**
     * 添加数据源
     **/
    @PostMapping("/addDs")
    public void add(){
        DynamicDataSourceContextHolder.push("test_druid");
        System.out.println("当前数据源：" + dynamicRoutingDataSource.determineDataSource());
        Map<String, DataSource> currentDataSources = dynamicRoutingDataSource.getCurrentDataSources();
        Set<String> currentDataSourceKey = currentDataSources.keySet();
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/dynamic_datasource?useUnicode=true&characterEncoding=utf" + "-8&useSSL=false");
        druidDataSource.setUsername("admin");
        druidDataSource.setPassword("admin");
        druidDataSource.setKeepAlive(true);
        druidDataSource.setMaxActive(10);
        druidDataSource.setMinIdle(2);
        druidDataSource.setValidationQuery("select 1");
        druidDataSource.setValidationQueryTimeout(600);
        // 如果不包含了当前数据源，才进行创建
        if (!currentDataSourceKey.contains("test_druid")) {
            dynamicRoutingDataSource.addDataSource("test_druid", druidDataSource);
        }
        System.out.println("添加后的所有数据源为：" + dynamicRoutingDataSource.getCurrentDataSources());

    }


    /**
     * 根据不同的数据源执行不同的sql
     * @param ds 数据源的名称或id
     * @return com.kanlon.response.CommonResponse
     **/
    @PostMapping("/exec-sql")
    public CommonResponse execSql(String ds) throws SQLException {
        DataSource dataSource2 = dynamicRoutingDataSource.getDataSource(ds);
        Statement statement = dataSource2.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from abs");
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCnt = resultSet.getMetaData().getColumnCount();
        // 遍历resultSet，将数据放入到list集合中
        List<Map<String,Object>> resultData = new ArrayList<>();
        while(resultSet.next()){
            Map<String,Object> map = new LinkedHashMap<>(16);
            for(int i=1;i<=columnCnt;i++){
                map.put(metaData.getColumnLabel(i),resultSet.getObject(i));
            }
            resultData.add(map);
        }
        return CommonResponse.succeedResult(resultData);
    }
}
