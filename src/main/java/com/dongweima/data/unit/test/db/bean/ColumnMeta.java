package com.dongweima.data.unit.test.db.bean;

import java.util.LinkedList;
import java.util.List;

public class ColumnMeta {

  private List<String> columns = new LinkedList<String>();

  public void addColumn(String column) {
    columns.add(column);
  }

  public List<String> getColumns() {
    List<String> list = new LinkedList<String>();
    list.addAll(this.columns);
    return list;
  }

  public ColumnMeta copy() {
    ColumnMeta columnMeta = new ColumnMeta();
    for (String column : columns) {
      columnMeta.addColumn(column);
    }
    return columnMeta;
  }
}
