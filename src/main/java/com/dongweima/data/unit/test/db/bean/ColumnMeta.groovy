package com.dongweima.data.unit.test.db.bean

class ColumnMeta {
  private List<String> list = new LinkedList<>()

  void addColumn(String column) {
    list.add(column)
  }

  List<String> getColumns(){
    List<String> list = new LinkedList<>()
    for (String column:this.list){
      list.add(column)
    }
    return list
  }

}
