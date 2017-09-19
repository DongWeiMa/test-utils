package com.dongweima.data.unit.test.db.init

import com.dongweima.data.unit.test.db.bean.ColumnValue
import com.dongweima.data.unit.test.db.bean.Data
import com.dongweima.utils.db.hbase.HbaseBase
import com.dongweima.utils.db.hbase.Row
import org.apache.hadoop.conf.Configuration

class HbaseInit implements Init {
  private Configuration conf
  private HbaseBase hbaseBase

  HbaseInit(Configuration conf) {
    this.conf = conf
    hbaseBase = new HbaseBase() {
      @Override
      Configuration getConf() {
        return conf
      }
    }
  }

  @Override
  void init(Data data) {
    if (hbaseBase == null) {
      hbaseBase = new HbaseBase() {
        @Override
        Configuration getConf() {
          return conf
        }
      }
    }
    String[] metas = (String[]) (data.getColumnMeta().getColumns().toArray())
    String[] families = new String[metas.length]
    String[] qualities = new String[metas.length]
    for (int i = 0; i < metas.length; i++) {
      String[] s = metas[i].trim().split(":")
      families[i] = s[0]
      qualities[i] = s[1]
    }
    data.getCloumnValues().each { ColumnValue columnValue ->
      String[] values = (String[]) (columnValue.getColumnValues().toArray())
      Row row = new Row()
      row.setRowKey(values[0])
      for (int i = 0; i < metas.length; i++) {
        row.setCellValue(families[i], qualities[i], values[i + 1])
      }
      hbaseBase.put(data.tableName,row)
    }
  }
}