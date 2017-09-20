package com.dongweima.data.unit.test.db.util

import com.dongweima.data.unit.test.db.bean.Data
import org.junit.Test

class DataFileProcessTest extends GroovyTestCase {
  DataFileProcess dataFileProcess = new DataFileProcess()
  @Test
  void testDealWithFile() {
    //todo搞清楚 baseDir在linux下是否是\
    String filePath = new File(PathUtil.getBaseDir(), "${File.separator}data${File.separator}hbase${File.separator}xyh_tag_condition").path
    Data result = dataFileProcess.readFile(filePath)
    assertDataIsRight result
  }

  /**
   * 只验证数据的有无
   * 数据的正确性应该交给对应的LineProcess处理器
   */
  private static  void assertDataIsRight(Data result) {
    assertEquals "xyh_tag_condition",result.tableName
    assertEquals "hbase", result.getType()
    assertTrue result.getColumnMeta().columns.size() > 0
    assertTrue result.getColumnValues().size() > 0
  }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme