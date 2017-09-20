package com.dongweima.data.unit.test.db.util

import com.dongweima.data.unit.test.db.bean.ColumnValue

class ColumnValueLineProcess implements LineProcess<ColumnValue> {
  @Override
  ColumnValue dealWithLine(String line) {
    ColumnValue columnValue = new ColumnValue()
    List<String> values = split(line, "|")
    for (String value : values) {
      value = value.trim()
      if (value != null)
        columnValue.addColumnValue(value.trim())
    }
    return columnValue
  }

  private static List<String> split(String s, String sp) {
    s = s.trim()
    if (!s.endsWith("|")){
      s = s+"|"
    }
    List<String> list = new LinkedList<>()
    int i = 0
    while (i < s.length()) {
      int j = s.indexOf("|", i)
      String sub = s.substring(i, j).trim()
      list.add(sub)
      if (j + 1 > i)
        i = j + 1
      else
        i++
    }
    return list
  }
}
