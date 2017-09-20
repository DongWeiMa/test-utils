package com.dongweima.data.unit.test.db.util

import com.dongweima.data.unit.test.db.bean.ColumnMeta

class ColumnMetaLineProcess implements LineProcess<ColumnMeta> {

  @Override
  ColumnMeta dealWithLine(String line) {
    if (line.contains("(")) {
      return hbaseColumnMeta(line.trim())
    }
    return mysqlColumnMeta(line.trim())
  }

  private  static ColumnMeta hbaseColumnMeta(String line) {
    List<String> list = new LinkedList<>()
    int i = 0
    while (i < line.length()) {
      int j = line.indexOf(")", i)
      String sub = line.substring(i, j)
      list.add(sub + ")")
      i = j + 1
    }

    ColumnMeta columnMeta = new ColumnMeta()
    for (String column : list) {
      column = column.trim()
      if (column.length() == 0)
        continue
      String family = column.substring(0, column.indexOf("("))
      String[] qualifies = column.substring(column.indexOf("(") + 1, column.length() - 1).split(",")
      for (String qualify : qualifies) {
        columnMeta.addColumn(family.trim() + ":" + qualify.trim())
      }
    }
    return columnMeta
  }

  private static ColumnMeta mysqlColumnMeta(String line) {
    String[] list = line.split(",")
    ColumnMeta columnMeta = new ColumnMeta()
    for (String column : list) {
      column = column.trim()
      if (column.length() == 0)
        continue
      columnMeta.addColumn(column.trim())
    }
    return columnMeta
  }

  static void main(String[] a) {
    ColumnMetaLineProcess cmp = new ColumnMetaLineProcess()
    ColumnMeta columnMeta = cmp.dealWithLine(" base( ptRule, family, quality )  org ( schoolId, structId )")
    println columnMeta
    columnMeta = cmp.dealWithLine(" ptRule , family , quality , schoolId , structId ")
    println columnMeta
  }
}
