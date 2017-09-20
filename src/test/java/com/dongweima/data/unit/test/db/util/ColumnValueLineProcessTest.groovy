package com.dongweima.data.unit.test.db.util

import com.dongweima.data.unit.test.db.bean.ColumnValue
import org.junit.Test

class ColumnValueLineProcessTest extends GroovyTestCase {
  ColumnValueLineProcess columnValueLineProcess = new ColumnValueLineProcess()

  @Test
  void test_use_standard_line_which_split_with_1() {
    ColumnValue result = columnValueLineProcess.dealWithLine("role:1          |every_day      |count         |   every_day_uv|             1|      schoolId1|        structId1")
    assertEquals 7, result.values.size()
  }
  @Test
  void test_use_special_line_which_has_null_cell() {
    ColumnValue result = columnValueLineProcess.dealWithLine("1||||||s")
    assertEquals 7, result.values.size()
  }
  @Test
  void test_use_special_line_which_end_with_1() {
    ColumnValue result = columnValueLineProcess.dealWithLine("1|1|1|1|1|1|s|")
    assertEquals 7, result.values.size()
  }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme