package com.dongweima.data.unit.test.db.bean
//todo 考虑是否加上copy 初始化到底怎么处理
/**
 * data可能是一张表的数据
 * 也可能是一堆sql
 */
class Data {
  private String tableName
  private String type
  private ColumnMeta columnMeta
  private List<ColumnValue> columnValues = new LinkedList<>()

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
    return columnMeta.copy()
  }

  void setColumnMeta(ColumnMeta columnMeta) {
    this.columnMeta = columnMeta
  }

  List<ColumnValue> getColumnValues() {
    List<ColumnValue> list = new LinkedList<>()
    for (ColumnValue cv : columnValues) {
      ColumnValue columnValue = cv.copy()
      list.add(columnValue)
    }
    return list
  }

  void addColumnValue(ColumnValue value){
    columnValues.add(value)
  }
}
