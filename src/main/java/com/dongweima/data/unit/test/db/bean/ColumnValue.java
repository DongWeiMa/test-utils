package com.dongweima.data.unit.test.db.bean;

import java.util.LinkedList;
import java.util.List;

public class ColumnValue {

  private List<String> values = new LinkedList<String>();

  public void addColumnValue(String value) {
    values.add(value);
  }

  public List<String> getColumnValues() {
    List<String> list = new LinkedList<String>();
    list.addAll(this.values);
    return list;
  }

  public ColumnValue copy() {
    ColumnValue columnValue = new ColumnValue();
    for (String value : values) {
      columnValue.addColumnValue(value);
    }
    return columnValue;
  }
}
