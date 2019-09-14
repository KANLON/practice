/**
 * Copyright © 2018 organization baomidou
 * <pre>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <pre/>
 */
package com.kanlon.configure;

import com.baomidou.dynamic.datasource.provider.AbstractDataSourceProvider;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * JDBC数据源提供者(抽象)
 *
 * @author zhangcanlong
 * @since 2019-09-14
 */
@Slf4j
@Configuration
public  class MysqlDataSourceProvider extends AbstractDataSourceProvider implements
    DynamicDataSourceProvider {

  /**
   * JDBC driver
   */
  private String driverClassName = "com.mysql.jdbc.Driver";
  /**
   * JDBC url 地址
   */
  private String url = "jdbc:mysql://127.0.0.1:3306/dynamic_datasource?useUnicode=true&characterEncoding=utf-8&useSSL"
          + "=false";
  /**
   * JDBC 用户名
   */
  private String username = "admin";
  /**
   * JDBC 密码
   */
  private String password = "admin";


  @Override
  public Map<String, DataSource> loadDataSources() {
    Connection conn = null;
    Statement stmt = null;
    try {
      Class.forName(driverClassName);
      log.info("成功加载数据库驱动程序");
      conn = DriverManager.getConnection(url, username, password);
      log.info("成功获取数据库连接");
      stmt = conn.createStatement();
      Map<String, DataSourceProperty> dataSourcePropertiesMap = executeStmt(stmt);
      return createDataSourceMap(dataSourcePropertiesMap);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      JdbcUtils.closeConnection(conn);
      JdbcUtils.closeStatement(stmt);
    }
    return null;
  }

  /**
   * 执行语句获得数据源参数
   *
   * @param statement 语句
   * @return 数据源参数
   * @throws SQLException sql异常
   */
  protected  Map<String, DataSourceProperty> executeStmt(Statement statement)
          throws SQLException{
    Map<String, DataSourceProperty> returnMap = new LinkedHashMap<>(16);
    String sql = " select id,name,host,port,db_username,db_password,database_name from d_db_info where is_deleted=0";
    statement.execute(sql);
    ResultSet resultSet = statement.getResultSet();
    while (resultSet.next()) {
      Long id = resultSet.getLong("id");
      String host = resultSet.getString("host");
      String port = resultSet.getString("port");
      String dbName = resultSet.getString("name");
      String dbUsername = resultSet.getString("db_username");
      String dbPassword = resultSet.getString("db_password");
      String dataBaseName = resultSet.getString("database_name");

      DataSourceProperty dataSourceProperty = new DataSourceProperty();
      dataSourceProperty.setPassword(dbPassword);
      dataSourceProperty.setUsername(dbUsername);
      dataSourceProperty.setUrl("jdbc:mysql://" + host + ":" + port + "/" + dataBaseName + "?useUnicode=true" +
              "&characterEncoding=utf-8&useSSL=false");
      dataSourceProperty.setDriverClassName("com.mysql.jdbc.Driver");
      if (id == 1) {
        returnMap.put("master", dataSourceProperty);
      } else {
        returnMap.put(String.valueOf(id), dataSourceProperty);
      }
    }
    return returnMap;
  }
}
