package com.dongweima.data.unit.test.db.bean

import spock.lang.Specification

class ColumnMetaTest extends Specification {
  ColumnMeta columnMeta

  def setup() {
    columnMeta = new ColumnMeta()
    columnMeta.addColumn("1")
    columnMeta.addColumn("2")
  }

  def "when add column after copy should effect the copy"() {
    when:
    ColumnMeta result = columnMeta.copy()
    result.addColumn("3")
    columnMeta.addColumn("4")
    columnMeta.addColumn("5")

    then:
    result.getColumns().size() == 3
    columnMeta.getColumns().size() == 4
  }

  def "get columns should return the new list"() {
    when:
    List<String> columns = columnMeta.getColumns()
    columns.add("4")
    then:
    columns.size() == 3
    columnMeta.columns.size() == 2
  }
}

