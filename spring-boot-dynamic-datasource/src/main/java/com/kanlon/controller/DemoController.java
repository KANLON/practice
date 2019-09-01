package com.kanlon.controller;

import com.kanlon.configure.MysqlDataSourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;


public class DemoController {

    @Autowired
    private javax.sql.DataSource dataSource;

    @PostMapping("/addDs")
    public void add(){
        MysqlDataSourceProvider dynamicDatasource=(MysqlDataSourceProvider) dataSource;
    }
}
