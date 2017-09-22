package com.dongweima.data.unit.test.db.util

import com.dongweima.data.unit.test.db.bean.Data
import com.dongweima.utils.file.PathUtil
import spock.lang.Specification

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class DataFileProcessTest extends Specification {
  private DataFileProcess dataFileProcess = new DataFileProcess()

  def "test deal with file"() {
    given:
    String filePath = new File(PathUtil.getBaseDir(), "/data/hbase/xyh_tag_condition").path

    when:
    Data result = dataFileProcess.readFile(filePath)

    then:
    assertDataIsRight result
  }

  /**
   * 只验证数据的有无
   * 数据的正确性应该交给对应的LineProcess处理器
   */
  private static void assertDataIsRight(Data result) {
    assertEquals "xyh_tag_condition", result.tableName
    assertEquals "hbase", result.getType()
    assertTrue result.getColumnMeta().columns.size() > 0
    assertTrue result.getColumnValues().size() > 0
  }
}

