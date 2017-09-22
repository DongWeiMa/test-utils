package com.dongweima.data.unit.test.db.init;

import com.dongweima.data.unit.test.db.bean.Data;
import com.dongweima.data.unit.test.db.mysql.MysqlTestUtil;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MysqlInit implements Init {

  private static Logger logger = LoggerFactory.getLogger(MysqlInit.class);

  @Override
  public void init(Data data) {
    try {
      MysqlTestUtil.insertBatch(data);
    } catch (Exception e) {
      logger.error("init mysql data failed ; data is : {}", data);
    }
  }

  void init(List<String> sqls) {
    for (String sql : sqls) {
      try {
        MysqlTestUtil.execute(sql);
      } catch (Throwable throwable) {
        logger.error(throwable.getMessage(), throwable);
      }
    }
  }
}
