package com.dongweima.data.unit.test.db.mysql;

import com.dongweima.data.unit.test.db.bean.ColumnValue;
import com.dongweima.data.unit.test.db.bean.Data;
import com.dongweima.data.unit.test.db.config.H2Config;
import com.dongweima.data.unit.test.db.util.SqlUtil;
import groovy.sql.GroovyRowResult;
import groovy.sql.Sql;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

/**
 * 这个是下策  正确的方式应该是子类字节把sql和结果的封装类传过来  直接返回结果集 这个类主要用于验证结果的，所以一般是query的封装
 */
public class MysqlTestUtil {

  private static Sql sql;

  private static DataSource getDatasource() {
    return H2Config.getH2Datasource();
  }

  public static Sql getSql() {
    if (sql == null) {
      sql = new Sql(getDatasource());
    }
    return sql;
  }

  public static List<GroovyRowResult> query(String sql) throws SQLException {
    return getSql().rows(sql);
  }

  public static GroovyRowResult queryForOne(String sql) throws SQLException {
    List<GroovyRowResult> list = getSql().rows(sql);
    if (list != null && list.size() > 0) {
      return list.get(0);
    }
    return null;
  }

  public static void execute(String sql) throws SQLException {
    getSql().execute(sql);
  }

  public static void insertBatch(Data data) throws SQLException {
    for (ColumnValue columnValue : data.getColumnValues()) {
      String sql = SqlUtil.buildInsertSql(data.getTableName(), data.getColumnMeta(), columnValue);
      getSql().execute(sql);
    }
  }
}
