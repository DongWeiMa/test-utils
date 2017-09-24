package com.dongweima.data.unit.test.db.util;

import com.dongweima.data.unit.test.db.bean.ColumnMeta;
import com.dongweima.data.unit.test.db.bean.ColumnValue;
import java.util.List;

public class SqlUtil {

  private SqlUtil() {

  }

  public static String buildInsertSql(String tableName, ColumnMeta columnMeta,
      ColumnValue columnValue) {
    StringBuilder metaSql = new StringBuilder();
    StringBuilder valueSql = new StringBuilder();
    List<String> values = columnValue.getColumnValues();
    List<String> metas = columnMeta.getColumns();
    int lastIndex = values.size() - 1;
    int i = -1;
    for (String value : values) {
      i++;
      if (value != null && !value.equals("")) {
        metaSql.append(metas.get(i));
        valueSql.append("'").append(value).append("'");
        if (!value.equals(values.get(lastIndex))) {
          valueSql.append(",");
          metaSql.append(",");
        }
      }
    }
    StringBuilder sql = new StringBuilder();
    sql.append("insert into ").
        append(tableName).
        append(" (").
        append(metaSql).
        append(") values(").
        append(valueSql).
        append(")");

    return sql.toString();
  }
}
