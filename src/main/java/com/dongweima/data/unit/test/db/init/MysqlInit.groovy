package com.dongweima.data.unit.test.db.init

import com.dongweima.data.unit.test.db.bean.Data
import com.dongweima.data.unit.test.db.mysql.AbstractDaoTestBase

import javax.sql.DataSource

class MysqlInit  extends AbstractDaoTestBase implements Init {
  private DataSource dataSource

  MysqlInit(DataSource dataSource) {
    this.dataSource = dataSource
  }

  @Override
  protected DataSource getDatasource() {
    return dataSource
  }

  @Override
  void init(Data data) {
    insertBatch(data)
  }
}
