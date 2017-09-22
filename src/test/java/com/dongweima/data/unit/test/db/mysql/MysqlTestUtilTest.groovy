package com.dongweima.data.unit.test.db.mysql

import com.dongweima.data.unit.test.db.bean.Data
import com.dongweima.data.unit.test.db.config.H2Config
import com.dongweima.data.unit.test.db.init.DataInitScript
import groovy.sql.GroovyRowResult
import spock.lang.Specification

import static org.junit.Assert.assertEquals

@SuppressWarnings("all")
class MysqlTestUtilTest extends Specification {

  void setup() {
    DataInitScript.initAll(true)
  }

  void "test insert batch"() {
    given:
    Data data = DataInitScript.getData("test" + File.separator + "test")

    when:
    MysqlTestUtil.insertBatch(data)
    List<GroovyRowResult> list = MysqlTestUtil.query("select * from test")

    then:
    assertEquals 2, list.size()

    cleanup:
    H2Config.getSql().execute("DELETE FROM test WHERE id=1 OR id=2")
  }
}
