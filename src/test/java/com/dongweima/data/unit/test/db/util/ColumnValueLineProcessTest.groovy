package com.dongweima.data.unit.test.db.util

import com.dongweima.data.unit.test.db.bean.ColumnValue
import spock.lang.Specification

import static org.junit.Assert.assertEquals

class ColumnValueLineProcessTest extends Specification {
  private ColumnValueLineProcess columnValueLineProcess = new ColumnValueLineProcess()

  def "test use standard line which split with 1"() {
    when:
    ColumnValue result = columnValueLineProcess.dealWithLine("role:1          |every_day      |count         |   every_day_uv|             1|      schoolId1|        structId1")

    then:
    assertEquals 7, result.values.size()
  }

  def "test use special line which has null cell"() {
    when:
    ColumnValue result = columnValueLineProcess.dealWithLine("1||||||s")

    then:
    assertEquals 7, result.values.size()
  }


  def "test use special line which end with 1"() {
    when:
    ColumnValue result = columnValueLineProcess.dealWithLine("1|1|1|1|1|1|s|")

    then:
    assertEquals 7, result.values.size()
  }
}
