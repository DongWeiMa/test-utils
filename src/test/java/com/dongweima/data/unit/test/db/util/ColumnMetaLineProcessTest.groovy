package com.dongweima.data.unit.test.db.util

import com.dongweima.data.unit.test.db.bean.ColumnMeta
import spock.lang.Specification

import static org.junit.Assert.assertArrayEquals

class ColumnMetaLineProcessTest extends Specification {
  private ColumnMetaLineProcess columnMetaLineProcess = new ColumnMetaLineProcess()

  void "test hbase meta"() {
    given:
    def arr = ["base:ptRule", "base:family", "base:quality", "org:schoolId", "org:structId"].toArray()

    when:
    ColumnMeta result = columnMetaLineProcess.dealWithLine("base(ptRule,family,quality) org(schoolId,structId)")

    then:
    assertArrayEquals arr, result.getColumns().toArray()
  }


  void "test mysql meta"() {
    given:
    def arr = ["1", "2", "3"].toArray()

    when:
    ColumnMeta result = columnMetaLineProcess.dealWithLine("1,2,3")

    then:
    assertArrayEquals arr, result.getColumns().toArray()
  }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme