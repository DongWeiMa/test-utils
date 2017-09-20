package com.dongweima.data.unit.test.db.mysql

import com.dongweima.data.unit.test.db.bean.ColumnValue
import com.dongweima.data.unit.test.db.bean.Data
import com.dongweima.data.unit.test.db.util.SqlUtil
import groovy.sql.GroovyRowResult
import groovy.sql.Sql

import javax.sql.DataSource

/**
 * 这个是下策  正确的方式应该是子类字节把sql和结果的封装类传过来  直接返回结果集
 * 这个类主要用于验证结果的，所以一般是query的封装
 */
abstract class AbstractDao {
  protected Sql sql

  protected abstract DataSource getDatasource()

  protected Sql getSql() {
    if (sql == null) {
      sql = new Sql(getDatasource())
    }
    return sql
  }

  protected List<GroovyRowResult> query(String sql) {
    return getSql().rows(sql)
  }

  protected GroovyRowResult queryForOne(String sql) {
    List<GroovyRowResult> list = getSql().rows(sql)
    if (list != null && list.size() > 0)
      return list.first()
    return null
  }

  protected void execute(String sql) {
    getSql().execute(sql)
  }

  protected void insertBatch(Data data) {
    for (ColumnValue columnValue : data.getColumnValues()) {
      String sql = SqlUtil.buildInsertSql(data.tableName, data.getColumnMeta(), columnValue)
      getSql().execute(sql)
    }
  }
}
