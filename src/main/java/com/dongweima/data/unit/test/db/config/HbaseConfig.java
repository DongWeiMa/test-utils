package com.dongweima.data.unit.test.db.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseTestingUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class HbaseConfig {

  private static Logger logger = LoggerFactory.getLogger(HbaseConfig.class);

  @Bean
  @Lazy
  public Configuration hbaseConfiguration() {
    start();
    return getTestUtil().getConfiguration();
  }

  private static int count = 0;
  private static final HBaseTestingUtility testUtil = new HBaseTestingUtility();

  private static void start() {
    if (count == 0) {
      synchronized (HbaseConfig.class) {
        if (count == 0) {
          try {
            //todo 考虑将这个设置变量改造成在window下才设置 这个变量可配置
            System.setProperty("hadoop.home.dir", "d:\\winutil\\");
            count++;
            testUtil.startMiniCluster();
          } catch (Throwable throwable) {
            logger.error("hbase mini 集群启动失败", throwable);
          }
        }
      }
    }
  }

  public static HBaseTestingUtility getTestUtil() {
    if (count == 0) {
      start();
    }
    return testUtil;
  }

}
