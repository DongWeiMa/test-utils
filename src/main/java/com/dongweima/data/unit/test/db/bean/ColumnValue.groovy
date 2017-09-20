package com.dongweima.data.unit.test.db.bean

class ColumnValue {
  List<String> values = new LinkedList<>()
  void addColumnValue(String value){
    values.add(value)
  }
  List<String> getColumnValues(){
    List<String> list = new LinkedList<>()
    for (String value : this.values) {
      list.add(value)
    }
    return list
  }

  ColumnValue copy() {
    ColumnValue columnValue = new ColumnValue()
    for (String value : values) {
      columnValue.addColumnValue(value)
    }
    return columnValue
  }

}
