package com.dongweima.data.unit.test.db.util;

import com.dongweima.data.unit.test.db.bean.ColumnMeta;
import com.dongweima.data.unit.test.db.bean.ColumnValue;
import java.util.List;

public class SqlUtil {

  private SqlUtil() {

  }

  public static String buildInsertSql(String tableName, ColumnMeta columnMeta,
      ColumnValue columnValue) {
    StringBuilder sql = new StringBuilder("insert into " + tableName + " (");
    List<String> list = columnMeta.getColumns();
    int lastIndex = list.size() - 1;
    for (String columnName : list) {
      sql.append(columnName);
      if (!columnName.equals(list.get(lastIndex))) {
        sql.append(",");
      }
    }
    StringBuilder s = new StringBuilder(sql + ") values(");
    List<String> values = columnValue.getColumnValues();
    lastIndex = values.size() - 1;
    for (String value : values) {
      s.append("'").append(value).append("'");
      if (!value.equals(values.get(lastIndex))) {
        s.append(",");
      }
    }
    s.append(") ");
    return s.toString();
  }
}
