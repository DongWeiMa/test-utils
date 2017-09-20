package com.dongweima.data.unit.test.db.bean

import spock.lang.Specification

class ColumnValueTest extends Specification {
  ColumnValue columnValue

  def setup() {
    columnValue = new ColumnValue()
    columnValue.addColumnValue("1")
    columnValue.addColumnValue("2")
  }

  def "test copy"() {
    when:
    ColumnValue result = columnValue.copy()
    result.addColumnValue("3")
    result.addColumnValue("4")
    columnValue.addColumnValue("3")
    then:
    result.values.size() == 4

    columnValue.values.size() == 3
  }

  def "get colunm should return new list"() {
    when:
    List<String> result = columnValue.getColumnValues()
    result.add("3")
    result.add("4")
    columnValue.addColumnValue("3")

    then:
    columnValue.values.size() == 3
    result.size() == 4
  }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme