package com.dongweima.data.unit.test.db.mysql

import com.dongweima.data.unit.test.db.bean.Data
import com.dongweima.data.unit.test.db.init.FileProcess
import groovy.sql.GroovyRowResult
import org.h2.tools.Server
import org.junit.Before
import org.junit.Test
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType

import javax.sql.DataSource

import static org.junit.Assert.assertEquals

class AbstractDaoTestBaseTest  extends AbstractDaoTestBase{
  DataSource dataSource
  @Before
  void before() {
    EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder()
    dataSource = databaseBuilder
      .setType(EmbeddedDatabaseType.H2)
      .build()
    String sql = "CREATE TABLE `test` (\n" +
      "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
      "  `name` varchar(255) DEFAULT NULL,\n" +
      "  PRIMARY KEY (`id`)\n" +
      ") ENGINE=InnoDB  DEFAULT CHARSET=utf8; "
    execute(sql)
    Server server = Server
      .createWebServer("-web", "-webAllowOthers", "-webDaemon", "-webPort", "3306")
    server.start()
  }
  @Override
  protected DataSource getDatasource() {
    return dataSource
  }

  @Test
  void test(){
    Data data = getData("data"+File.separator+"mysql"+File.separator+"test")
    insertBatch(data)
    List<GroovyRowResult> list = query("select * from test")
    assertEquals 2,list.size()
  }

  private static Data getData(String fileName){
    return FileProcess.dealFileWithResourcePath(fileName)
  }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme