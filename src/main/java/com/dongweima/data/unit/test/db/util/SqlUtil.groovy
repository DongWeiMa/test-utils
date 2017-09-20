package com.dongweima.data.unit.test.db.util

import com.dongweima.data.unit.test.db.bean.ColumnMeta
import com.dongweima.data.unit.test.db.bean.ColumnValue

class SqlUtil {
  private SqlUtil() {

  }
  static String buildInsertSql(String tableName, ColumnMeta columnMeta, ColumnValue columnValue) {
    String sql = "insert into ${->tableName} ("
    for (String columnName : columnMeta.columns) {
      sql = sql + columnName
      if (columnName != columnMeta.getColumns().last()) {
        sql = sql + ","
      }
    }
    String s = sql + ") values("
    List<String> values = columnValue.getColumnValues()
    for (String value : values) {
      s = s + "'"+value+"'"
      if (value != values.last()) {
        s = s + ","
      }
    }
    s = s + ") "
    return s
  }
}
