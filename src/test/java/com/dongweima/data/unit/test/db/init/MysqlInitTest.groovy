package com.dongweima.data.unit.test.db.init

import com.dongweima.data.unit.test.db.config.H2Config
import groovy.sql.GroovyRowResult
import spock.lang.Specification

@SuppressWarnings("all")
class MysqlInitTest extends Specification {

  private MysqlInit mysqlInit = new MysqlInit()

  def setup() {
    DataInitScript.initAll(true)
  }

  def "init use fileData  which include 2 row data"() {
    when:
    mysqlInit.init(data)
    List<GroovyRowResult> list = H2Config.getSql().rows("select id,name from test where id=2 or id=1")

    then:
    list.size() == 2

    cleanup:
    H2Config.getSql().execute("delete from test where id=1 or id=2")

    where:
    data                                                | _
    DataInitScript.getDataWithResourcePath("test/test") | _

  }


  def "init use standard inser sql"() {
    when:
    mysqlInit.init(data)
    List<GroovyRowResult> list = H2Config.getSql().rows("select id,name from test where id=4")
    GroovyRowResult result = list.get(0)

    then:
    result.get("id").equals 4
    result.get("name").equals "a"

    cleanup:
    H2Config.getSql().execute("delete from test where id = 4")
    where:
    data                                           | _
    ["insert into test (id,name) values('4','a')"] | _
  }
}

