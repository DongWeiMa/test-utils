package com.dongweima.data.unit.test.db.bean
//todo 考虑是否加上copy 初始化到底怎么处理
class Data {
  private String tableName
  private String type
  private ColumnMeta columnMeta
  private List<ColumnValue> cloumnValues  = new LinkedList<>()

  String getTableName() {
    return tableName
  }

  void setTableName(String tableName) {
    this.tableName = tableName
  }

  String getType() {
    return type
  }

  void setType(String type) {
    this.type = type
  }

  ColumnMeta getColumnMeta() {
    return columnMeta
  }

  void setColumnMeta(ColumnMeta columnMeta) {
    this.columnMeta = columnMeta
  }

  List<ColumnValue> getCloumnValues() {
    return cloumnValues
  }

  void addColumnValue(ColumnValue value){
    cloumnValues.add(value)
  }
}
