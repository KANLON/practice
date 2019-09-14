package com.kanlon.controller;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.kanlon.response.CommonResponse;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

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
public class DataSourceController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DynamicRoutingDataSource dynamicRoutingDataSource;

    /**
     * 添加数据源
     **/
    @PostMapping("/addDs")
    public void add(){
        DynamicDataSourceContextHolder.push("sdf");
        System.out.println("目前的数据源为："+dynamicRoutingDataSource.getCurrentDataSources());
        PooledDataSourceFactory dataSourceFactory = new PooledDataSourceFactory();
        // 设置数据源的属性
        dataSourceFactory.setProperties(new Properties());
        dataSourceFactory.getDataSource();
        dynamicRoutingDataSource.addDataSource("test",dataSource);
        System.out.println("添加后的数据源为："+dynamicRoutingDataSource.getCurrentDataSources());

    }


    /**
     * 根据不同的数据源执行不同的sql
     * @param ds 数据源的名称或id
     * @return com.kanlon.response.CommonResponse
     **/
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
