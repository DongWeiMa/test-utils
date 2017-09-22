package com.dongweima.data.unit.test.db.hbase

import com.dongweima.utils.db.hbase.HbaseObj
import com.dongweima.utils.db.hbase.Row
import org.apache.hadoop.hbase.filter.FilterList
import org.apache.hadoop.hbase.filter.PageFilter
import org.junit.Test
import spock.lang.Specification

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertFalse

//todo 检查修改
class HbaseTestUtilTest extends Specification {

  private static final String user_group = "user_group:user_event"

  // run before the first feature method
  void setupSpec() throws Throwable {
    HbaseTestUtil.createNameSpace("user_group")
    String[] families = ["user", "1", "2", "3", "4", "5"]
    HbaseTestUtil.createTable("user_group:user_event", families)
    HbaseTestUtil.createTable("test", families)
    //生成数据
    createData()
  }

  /**
   * 数据说明 3个事件 事件元数据定义在mysql表中 分别是 阅读事件，点击事件 这两种元事件数据 2个阅读事件 两个点击事件 一个色彩事件的上层重复事件
   */
  private static void createData() throws Throwable {
    Long groupId = 1L
    for (int i = 0; i < 10; i++) {
      Row row = new Row()
      String userId = Integer.toString(i)
      row.setRowKey("000000000" + groupId + userId)
      row.setCellValue("user", "id", userId)
      row.setCellValue("user", "name", "userName" + userId)
      row.setCellValue("1", "code", Integer.toString(i % 2))
      row.setCellValue("2", "code", Integer.toString(i % 3))
      //为了测试默认值 默认只有i为偶数才有code
      if (i % 2 == 0) {
        row.setCellValue("3", "code", Integer.toString(i % 2))
      }
      HbaseTestUtil.put(user_group, row)
    }
  }


  @Test
  void "test drop table"() throws Exception {
    when:
    HbaseTestUtil.dropTable("test")

    then:
    assertEquals(false, HbaseTestUtil.tableExist("test"))
  }

  @Test
  void "test create table"() throws Throwable {
    given:
    String tableName = "createTable"
    String[] familes = new String[1]
    familes[0] = "family"

    when:
    HbaseTestUtil.createTable(tableName, familes)

    then:
    assertEquals(true, HbaseTestUtil.tableExist(tableName))
  }

  void "test table exist"() {
    given:
    String tableName1 = "hhhh"
    String tableName2 = user_group

    expect:
    assertEquals(false, HbaseTestUtil.tableExist(tableName1))
    assertEquals(true, HbaseTestUtil.tableExist(tableName2))
  }

  def "test put"() {
    given:
    Row row = new Row()
    String rowKey = "00000000091"
    row.setRowKey(rowKey)
    row.setCellValue("user", "id", Integer.toString(1))

    when:
    HbaseTestUtil.put(user_group, row)
    HbaseObj obj = new HbaseObj()
      .buildQulifier("user", "id")
      .buildTableName(user_group)
      .buildStartRow(rowKey)
      .buildEndRow(rowKey)
    List<Row> list = HbaseTestUtil.scan(obj)

    then:
    assertEquals(list.size(), 1)
  }


  void "should return the all qualifier in the family when scan"() throws Exception {
    given:
    //选中 一个列簇 应当返回该列簇所有列
    HbaseObj obj = new HbaseObj()
      .buildTableName(user_group)
      .buildFamily("user")
      .buildStartRow("00000000011")
      .buildEndRow("00000000019")

    when:
    List<Row> result = HbaseTestUtil.scan(obj)

    then:
    assertEquals(8, result.size())
    assertEquals(2, result.get(0).getFamilies().get(0).getQualifiers().size())
  }

  @Test
  void "should return one qualifier in the family when we ask one qualifier although we set family"() {
    given:
    //选中 一个列簇 应当返回该列簇所有列
    HbaseObj obj = new HbaseObj()
      .buildTableName(user_group)
      .buildFamily("user")//这个是显式设定列簇 但是实际已经在qualifier设定的时候已经隐式设定了
      .buildQulifier("user", "id")
      .buildStartRow("00000000011")
      .buildEndRow("00000000019")

    when:
    List<Row> result = HbaseTestUtil.scan(obj)

    then:
    assertEquals(8, result.size())
    assertEquals(1, result.get(0).getFamilies().get(0).getQualifiers().size())
  }


  void "should return 2 or more lines when i user page filter limit 2 and no set end row"() {
    given:
    HbaseObj obj = new HbaseObj()
      .buildTableName(user_group)
      .buildFamily("user")//这个是显式设定列簇 但是实际已经在qualifier设定的时候已经隐式设定了
      .buildStartRow("00000000011")
      .buildFilter(new FilterList(new PageFilter(2)))

    when:
    List<Row> result = HbaseTestUtil.scan(obj)

    then:
    assertFalse(2 > result.size())
    assertEquals("00000000011", result.get(0).getRowKey())
    assertEquals("00000000012", result.get(1).getRowKey())
  }


  void "simple scan"() {
    given:
    List<Row> list = HbaseTestUtil.simpleScan(user_group, "00000000013", "00000000014")

    expect:
    assertEquals(1, list.size())
  }

  @Test
  void "should return reversed list and 2 or more lines when i use page filter limit 2 and set isReversed true"() {
    given:
    HbaseObj obj = new HbaseObj()
      .buildTableName(user_group)
      .buildFamily("user")
      .buildStartRow("00000000013")
      .buildFilter(new FilterList(new PageFilter(2)))
      .buildIsReversed(true)

    when:
    List<Row> result = HbaseTestUtil.scan(obj)

    then:
    assertFalse(2 > result.size())
    assertEquals("00000000013", result.get(0).getRowKey())
    assertEquals("00000000012", result.get(1).getRowKey())
  }

  void "test create nameSpace"() throws Exception {
    given:
    String name = "creatNamespace"

    when:
    HbaseTestUtil.createNameSpace(name)

    then:
    assertEquals(true, HbaseTestUtil.namespaceExist(name))
  }

  void "test namespace exist"() {
    given:
    String name = "user_group"

    expect:
    assertEquals(true, HbaseTestUtil.namespaceExist(name))
    assertEquals(false, HbaseTestUtil.namespaceExist("hhhhh"))
  }
}
