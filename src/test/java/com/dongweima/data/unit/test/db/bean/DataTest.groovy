package com.dongweima.data.unit.test.db.bean

import spock.lang.Specification

class DataTest extends Specification {
  Data data

  def setup() {
    data = new Data()
    ColumnMeta columnMeta = new ColumnMeta()
    ColumnValue columnValue = new ColumnValue()
    data.addColumnValue(columnValue)
    data.setColumnMeta(columnMeta)
  }

  def "get columnMeta should a new columnMeta"() {
    when:
    ColumnMeta columnMeta = data.getColumnMeta()
    columnMeta.addColumn("1")
    then:
    columnMeta.columns.size() == 1
    data.columnMeta.columns.size() == 0
  }

  def "get columnValues should return a new list"() {
    when:
    List<ColumnValue> values = data.getColumnValues()
    values.add(new ColumnValue())
    then:
    values.size() == 2
    data.columnValues.size() == 1
  }
}
