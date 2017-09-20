package com.dongweima.data.unit.test.db.init;

import com.dongweima.data.unit.test.db.bean.Data;
import com.dongweima.data.unit.test.db.config.H2Config;
import com.dongweima.data.unit.test.db.mysql.AbstractDao;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MysqlInit extends AbstractDao implements Init {

  private static Logger logger = LoggerFactory.getLogger(MysqlInit.class);
  private DataSource dataSource;

  MysqlInit() {
    this.dataSource = H2Config.getH2Datasource();
  }

  @Override
  protected DataSource getDatasource() {
    return dataSource;
  }

  @Override
  public void init(Data data) {
    try {
      insertBatch(data);
    } catch (Exception e) {
      logger.error("init mysql data failed ; data is : {}", data);
    }
  }

  void init(List<String> sqls) {
    for (String sql : sqls) {
      try {
        execute(sql);
      } catch (Throwable throwable) {
        logger.error(throwable.getMessage(), throwable);
      }

    }
  }
}
