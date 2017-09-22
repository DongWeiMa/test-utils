package com.dongweima.data.unit.test.db.util

import com.dongweima.data.unit.test.db.bean.ColumnMeta
import com.dongweima.data.unit.test.db.bean.ColumnValue
import spock.lang.Specification

import static org.junit.Assert.assertEquals

class SqlUtilTest extends Specification {
  def "test build insertSql"() {
    given:
    ColumnMeta columnMeta = new ColumnMeta()
    columnMeta.addColumn("id")
    columnMeta.addColumn("name")
    ColumnValue columnValue = new ColumnValue()
    columnValue.addColumnValue("1")
    columnValue.addColumnValue("name")

    when:
    String sql = SqlUtil.buildInsertSql("tag", columnMeta, columnValue)

    then:
    assertEquals "insert into tag (id,name) values('1','name')", sql.trim()
  }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme