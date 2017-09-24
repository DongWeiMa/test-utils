package com.dongweima.data.unit.test.db.util

import com.dongweima.data.unit.test.db.bean.ColumnValue
import com.dongweima.data.unit.test.db.bean.Data
import com.dongweima.utils.file.PathUtil
import spock.lang.Specification
import spock.lang.Unroll

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull

class DataFileProcessTest extends Specification {
  private DataFileProcess dataFileProcess = new DataFileProcess()

  @Unroll
  def "使用相对路径来读data文件 #filePath"() {
    when:
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

  @Unroll
  def "使用绝对路径来读data文件 #filePath"() {
    when:
    filePath = new File(PathUtil.getBaseDir(), filePath).getPath()
    Data result = dataFileProcess.readFile(filePath)

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
    assertNotNull result.getType()
    assertNotNull result.getTableName()
    assertNotNull result.getColumnMeta()
    assertNotNull result.getColumnValues()
    for (ColumnValue columnValue : result.getColumnValues()) {
      assertEquals result.getColumnMetasSize(), columnValue.getColumnValues().size()
    }
  }
}

