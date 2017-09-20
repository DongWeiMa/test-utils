package com.dongweima.data.unit.test.db.init

import com.dongweima.data.unit.test.db.config.HbaseConfig
import com.dongweima.utils.db.hbase.HbaseBase
import com.dongweima.utils.db.hbase.HbaseObj
import com.dongweima.utils.db.hbase.Row
import org.apache.hadoop.conf.Configuration
import spock.lang.Specification

class HbaseInitTest extends Specification {

  HbaseInit hbaseInit
  HbaseBase hbaseBase

  def setup() {
    hbaseInit = new HbaseInit()
    hbaseBase = new HbaseBase() {
      @Override
      Configuration getConf() {
        return HbaseConfig.getTestUtil().getConfiguration()
      }
    }
  }

  def "test init"() {
    given:
    HbaseObj obj = new HbaseObj()
      .buildTableName("xyh_tag")
      .buildStartRow("role:1")
      .buildEndRow("role:3")

    when:
    hbaseInit.init(data)
    List<Row> list = hbaseBase.scan(obj)

    then:
    list.size() == 2

    cleanup:
    //todo clean hbase
    where:
    data                                                        | _
    DataInitScript.getData("test" + File.separator + "xyh_tag") | _
  }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme