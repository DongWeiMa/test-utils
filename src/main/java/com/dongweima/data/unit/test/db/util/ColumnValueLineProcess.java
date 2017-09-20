package com.dongweima.data.unit.test.db.util;

import com.dongweima.data.unit.test.db.bean.ColumnValue;
import java.util.LinkedList;
import java.util.List;

public class ColumnValueLineProcess implements LineProcess<ColumnValue> {

  @Override
  public ColumnValue dealWithLine(String line) {
    ColumnValue columnValue = new ColumnValue();
    List<String> values = split(line, "|");
    for (String value : values) {
      value = value.trim();
      columnValue.addColumnValue(value.trim());
    }
    return columnValue;
  }

  private static List<String> split(String s, String sp) {
    s = s.trim();
    if (!s.endsWith(sp)) {
      s = s + sp;
    }
    List<String> list = new LinkedList<String>();
    int i = 0;
    while (i < s.length()) {
      int j = s.indexOf(sp, i);
      String sub = s.substring(i, j).trim();
      list.add(sub);
      if (j + 1 > i) {
        i = j + 1;
      } else {
        i++;
      }
    }
    return list;
  }
}
