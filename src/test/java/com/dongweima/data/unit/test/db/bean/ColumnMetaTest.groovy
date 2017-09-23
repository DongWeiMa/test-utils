package com.dongweima.data.unit.test.db.bean

import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

class ColumnMetaTest extends Specification {
  private ColumnMeta columnMeta

  def setup() {
    columnMeta = new ColumnMeta()
    columnMeta.addColumn("1")
    columnMeta.addColumn("2")
  }

  def "right test: when add column after copy should effect the copy"() {
    when:
    ColumnMeta result = columnMeta.copy()
    result.addColumn("3")
    columnMeta.addColumn("4")
    columnMeta.addColumn("5")

    then:
    assertThat(result.getColumns())
      .hasSize(3)
      .contains("3")
    assertThat(columnMeta.getColumns())
      .hasSize(4)
      .contains("4", "5")

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

