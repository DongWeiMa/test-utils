package com.dongweima.data.unit.test.db.init;

import com.dongweima.data.unit.test.db.bean.ColumnValue;
import com.dongweima.data.unit.test.db.bean.Data;
import com.dongweima.data.unit.test.db.hbase.HbaseTestUtil;
import com.dongweima.utils.db.hbase.Row;
import java.util.HashSet;
import java.util.Set;

public class HbaseInit implements Init {

  @Override
  public void init(Data data) {

    Object[] metas = (data.getColumnMeta().getColumns().toArray());
    Set<String> familySet = new HashSet<String>();
    String[] families = new String[metas.length];
    String[] qualities = new String[metas.length];
    for (int i = 0; i < metas.length; i++) {
      String[] s = ((String) metas[i]).trim().split(":");
      familySet.add(s[0]);
      families[i] = s[0];
      qualities[i] = s[1];
    }
    createTable(data.getTableName(), familySet);
    for (ColumnValue columnValue : data.getColumnValues()) {
      Object[] values = (columnValue.getColumnValues().toArray());
      Row row = new Row();
      row.setRowKey((String) values[0]);
      for (int i = 0; i < metas.length; i++) {
        row.setCellValue(families[i], qualities[i], (String) values[i + 1]);
      }
      HbaseTestUtil.put(data.getTableName(), row);
    }

  }

  //自动创建表
  //但是可能列簇会少
  private void createTable(String tableName, Set<String> families) {
    if (!HbaseTestUtil.tableExist(tableName)) {
      String[] f = new String[families.size()];
      f = families.toArray(f);
      HbaseTestUtil.createTable(tableName, f);
    }
  }
}
