package com.dongweima.data.unit.test.db.bean

class ColumnValue {
  List<String> list = new LinkedList<>()
  void addColumnValue(String value){
    list.add(value)
  }
  List<String> getColumnValues(){
    List<String> list = new LinkedList<>()
    for(String value:this.list){
      list.add(value)
    }
    return list
  }

}
