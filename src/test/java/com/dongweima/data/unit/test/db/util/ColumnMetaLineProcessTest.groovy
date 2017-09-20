package com.dongweima.data.unit.test.db.util

import com.dongweima.data.unit.test.db.bean.ColumnMeta
import org.junit.Test

class ColumnMetaLineProcessTest  extends GroovyTestCase{
  ColumnMetaLineProcess columnMetaLineProcess = new ColumnMetaLineProcess()

  @Test
  void test_hbase_meta() {
   def arr = ["base:ptRule","base:family","base:quality","org:schoolId","org:structId"].toArray()
    ColumnMeta result = columnMetaLineProcess.dealWithLine("base(ptRule,family,quality) org(schoolId,structId)")
    assertArrayEquals arr,result.getColumns().toArray()
  }

  @Test
  void test_mysql_meta() {
    def arr = ["1","2","3"].toArray()
    ColumnMeta result = columnMetaLineProcess.dealWithLine("1,2,3")
    assertArrayEquals arr,result.getColumns().toArray()
  }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme