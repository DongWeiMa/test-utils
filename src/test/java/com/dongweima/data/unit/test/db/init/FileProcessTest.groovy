package com.dongweima.data.unit.test.db.init

import com.dongweima.data.unit.test.db.bean.Data
import org.junit.Test

class FileProcessTest extends GroovyTestCase {

  @Test
  void testDealWithFile() {
    //搞清楚 baseDir在linux下是否是\
    String baseDir = DataInitScript.class.getResource("/")
    baseDir = baseDir.substring(5, baseDir.length())
    String filePath = new File(baseDir,"${File.separator}data${File.separator}xyh_tag_condition").path
    Data result = FileProcess.dealWithFile(filePath)
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
    assertTrue result.getCloumnValues().size() > 0
  }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme