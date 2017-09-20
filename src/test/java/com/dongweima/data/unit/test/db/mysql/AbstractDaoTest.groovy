package com.dongweima.data.unit.test.db.mysql

import com.dongweima.data.unit.test.db.bean.Data
import com.dongweima.data.unit.test.db.config.H2Config
import com.dongweima.data.unit.test.db.init.DataInitScript
import groovy.sql.GroovyRowResult
import org.junit.Before
import org.junit.Test

import javax.sql.DataSource

import static org.junit.Assert.assertEquals

@SuppressWarnings("all")
class AbstractDaoTest extends AbstractDao {
  private DataSource dataSource

  @Before
  void before() {
    dataSource = H2Config.getH2Datasource()
    H2Config.serverStart()
    DataInitScript.initAll(true)
  }

  @Override
  protected DataSource getDatasource() {
    return dataSource
  }

  @Test
  void test_insert_batch() {
    Data data = DataInitScript.getData("test" + File.separator + "test")
    insertBatch(data)
    List<GroovyRowResult> list = query("select * from test")
    assertEquals 2, list.size()
    H2Config.getSql().execute("delete from test where id=1 or id=2")
  }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme