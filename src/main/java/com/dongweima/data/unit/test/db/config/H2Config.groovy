package com.dongweima.data.unit.test.db.config

import groovy.sql.Sql
import org.h2.tools.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType

import javax.sql.DataSource

//项目需要以boot的方式启动 否则无法读到这个类
@Configuration
class H2Config {
  private static Server server
  private static DataSource dataSource
  private static Sql sql

  //启动一个H2的web server， 调试时可以通过localhost:3306访问到H2的内容
  //JDBC URL: jdbc:h2:mem:testdb
  //User Name: sa
  //Password: 无
  //注意如果使用断点，断点类型(Suspend Type)一定要设置成Thread而不能是All,否则web server无法正常访问!
  @Profile("test")
  @Bean(name = "h2WebServer", destroyMethod = "stop")
  Server server() throws Exception {
    //在3306端口上启动一个web server
    serverStart()

    return server
  }

  static void serverStart() {
    serverInit()
    if (server.getStatus().startsWith("Not started")) {
      server.start()
    }
  }

  static DataSource getH2Datasource() {
    if (dataSource == null) {
      serverStart()
      EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder()
      dataSource = databaseBuilder
        .setType(EmbeddedDatabaseType.H2)
        .build()
    }
    return dataSource
  }

  static Sql getSql() {
    if (sql == null) {
      sql = new Sql(getH2Datasource())
    }
    return sql
  }

  private static void serverInit() {
    if (server == null) {
      server = Server
        .createWebServer("-web", "-webAllowOthers", "-webDaemon", "-webPort", "3306")
    }
  }
}
