package com.dongweima.data.unit.test.db.init

import com.dongweima.data.unit.test.db.bean.Data
import com.dongweima.data.unit.test.db.config.H2Config
import com.dongweima.data.unit.test.db.mysql.AbstractDao

import javax.sql.DataSource

class MysqlInit extends AbstractDao implements Init {
  private DataSource dataSource

  MysqlInit() {
    this.dataSource = H2Config.getH2Datasource()
  }

  @Override
  protected DataSource getDatasource() {
    return dataSource
  }

  @Override
  void init(Data data) {
    insertBatch(data)
  }

  void init(List<String> sqls) {
    for (String sql : sqls) {
      try {
        execute(sql)
      } catch (Throwable throwable) {
        println(throwable.getMessage())
        println(throwable)
      }

    }
  }
}
