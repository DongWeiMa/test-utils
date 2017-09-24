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

  def "对copy方法返回的结果进行测试 "() {
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

  def " 测试从columnMeta获取到的列在外部进行添加操作，内部会不会受到影响"() {
    when:
    List<String> columns = columnMeta.getColumns()
    columns.add("4")

    then:
    columns.size() == 3
    columnMeta.columns.size() == 2
  }
}

