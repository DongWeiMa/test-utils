package com.dongweima.data.unit.test.db.config

import org.h2.tools.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

//项目需要以boot的方式启动 否则无法读到这个类
@Configuration
class H2Config {

  @Profile("test")
  @Bean(name = "h2WebServer", initMethod = "start", destroyMethod = "stop")
  //启动一个H2的web server， 调试时可以通过localhost:3306访问到H2的内容
  //JDBC URL: jdbc:h2:mem:testdb
  //User Name: sa
  //Password: 无
  //注意如果使用断点，断点类型(Suspend Type)一定要设置成Thread而不能是All,否则web server无法正常访问!
  Server server() throws Exception {
    //在3306端口上启动一个web server
    Server server = Server
      .createWebServer("-web", "-webAllowOthers", "-webDaemon", "-webPort", "3306")
    return server
  }
}
