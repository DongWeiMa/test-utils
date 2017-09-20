package com.dongweima.data.unit.test.db.bean

class ColumnMeta {
  private List<String> columns = new LinkedList<>()

  void addColumn(String column) {
    columns.add(column)
  }

  List<String> getColumns() {
    List<String> list = new LinkedList<>()
    for (String column : this.columns) {
      list.add(column)
    }
    return list
  }

  ColumnMeta copy() {
    ColumnMeta columnMeta = new ColumnMeta()
    for (String column : columns) {
      columnMeta.addColumn(column)
    }
    return columnMeta

  }

}
