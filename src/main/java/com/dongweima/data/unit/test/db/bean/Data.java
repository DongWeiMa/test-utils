package com.dongweima.data.unit.test.db.bean;

import com.alibaba.fastjson.JSONObject;
import java.util.LinkedList;
import java.util.List;

public class Data {

  private String tableName;
  private String type;
  private ColumnMeta columnMeta;
  private List<ColumnValue> columnValues = new LinkedList<ColumnValue>();

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public ColumnMeta getColumnMeta() {
    return columnMeta.copy();
  }

  public void setColumnMeta(ColumnMeta columnMeta) {
    this.columnMeta = columnMeta;
  }

  public List<ColumnValue> getColumnValues() {
    List<ColumnValue> list = new LinkedList<ColumnValue>();
    for (ColumnValue cv : columnValues) {
      ColumnValue columnValue = cv.copy();
      list.add(columnValue);
    }
    return list;
  }

  public void removeColumnValue(int index) {
    columnValues.remove(index);
  }

  public void addColumnValue(ColumnValue value) {
    columnValues.add(value);
  }

  public int getColumnMetasSize() {
    if (type.equals("mysql")) {
      return columnMeta.getColumns().size();
    }
    return columnMeta.getColumns().size() + 1;
  }
  //todo test
  @Override
  public String toString() {
    return JSONObject.toJSONString(this);
  }
}
