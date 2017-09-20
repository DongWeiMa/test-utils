package com.dongweima.data.unit.test.db.util

import com.dongweima.data.unit.test.db.bean.ColumnMeta
import com.dongweima.data.unit.test.db.bean.ColumnValue
import org.junit.Test

import static org.junit.Assert.assertEquals

class SqlUtilTest {

  @Test
  void testBuildInsertSql() {
    ColumnMeta columnMeta = new ColumnMeta()
    columnMeta.addColumn("id")
    columnMeta.addColumn("name")
    ColumnValue columnValue = new ColumnValue()
    columnValue.addColumnValue("1")
    columnValue.addColumnValue("name")
    String sql = SqlUtil.buildInsertSql("tag",columnMeta,columnValue)
    assertEquals "insert into tag (id,name) values('1','name')",sql.trim()
  }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme