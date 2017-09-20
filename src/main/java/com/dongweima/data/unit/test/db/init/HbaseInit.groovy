package com.dongweima.data.unit.test.db.init

import com.dongweima.data.unit.test.db.bean.ColumnValue
import com.dongweima.data.unit.test.db.bean.Data
import com.dongweima.data.unit.test.db.config.HbaseConfig
import com.dongweima.utils.db.hbase.HbaseBase
import com.dongweima.utils.db.hbase.Row
import org.apache.hadoop.conf.Configuration

class HbaseInit implements Init {
  private HbaseBase hbaseBase

  @Override
  void init(Data data) {
    if (hbaseBase == null) {
      hbaseBase = new HbaseBase() {
        @Override
        Configuration getConf() {
          return HbaseConfig.getTestUtil().getConfiguration()
        }
      }
    }
    String[] metas = (String[]) (data.getColumnMeta().getColumns().toArray())
    Set<String> familySet = new HashSet<>()
    String[] families = new String[metas.length]
    String[] qualities = new String[metas.length]
    for (int i = 0; i < metas.length; i++) {
      String[] s = metas[i].trim().split(":")
      familySet.add(s[0])
      families[i] = s[0]
      qualities[i] = s[1]
    }
    createTable(data.getTableName(), (String[]) familySet.toArray())
    data.getColumnValues().each { ColumnValue columnValue ->
      String[] values = (String[]) (columnValue.getColumnValues().toArray())
      Row row = new Row()
      row.setRowKey(values[0])
      for (int i = 0; i < metas.length; i++) {
        row.setCellValue(families[i], qualities[i], values[i + 1])
      }
      hbaseBase.put(data.tableName, row)
    }
  }
  //自动创建表
  //但是可能列簇会少
  private createTable(String tableName, String[] families) {
    if (!hbaseBase.tableExist(tableName)) {
      hbaseBase.createTable(tableName, families)
    }
  }
}