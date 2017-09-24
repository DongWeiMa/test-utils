package com.dongweima.data.unit.test.db.util

import com.dongweima.data.unit.test.db.bean.ColumnValue
import com.dongweima.data.unit.test.db.bean.Data
import spock.lang.Specification
import spock.lang.Unroll

import static org.junit.Assert.assertEquals

class DataFileProcessTest extends Specification {
  private DataFileProcess dataFileProcess = new DataFileProcess()

  @Unroll
  def "test deal with file #filepath"() {
    when:
    println(filePath)
    Data result = dataFileProcess.readFileWithResourcePath(filePath)

    then:
    assertDataIsRight result

    where:
    filePath                       | _
    "data/hbase/xyh_tag_condition" | _
    "test/empty_column_mysql"      | _
    "test/empty_column_hbase"      | _
    "test/test"                    | _
    "test/xyh_tag"                 | _
  }

  /**
   * 只验证数据的有无
   * 数据的正确性应该交给对应的LineProcess处理器
   */
  private static void assertDataIsRight(Data result) {
    for (ColumnValue columnValue : result.getColumnValues()) {
      assertEquals result.getColumnMetasSize(), columnValue.getColumnValues().size()
    }
  }
}

