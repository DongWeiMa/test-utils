package com.dongweima.data.unit.test.db.init

import com.dongweima.data.unit.test.db.bean.Data
import com.dongweima.data.unit.test.db.util.PathUtil
import org.apache.hadoop.conf.Configuration
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.sql.DataSource

//这个仅适用于单数据源
class DataInitScript implements Runnable {
  private static Logger logger = LoggerFactory.getLogger(DataInitScript.class)
  private String baseDir
  private Init mysqlInit
  private Init hbaseInit

  DataInitScript() {
    initBaseDir()
  }

  DataInitScript(DataSource dataSource) {
    mysqlInit = new MysqlInit(dataSource)
    initBaseDir()
  }

  DataInitScript(Configuration conf) {
    hbaseInit = new HbaseInit(conf)
    initBaseDir()
  }

  DataInitScript(DataSource dataSource, Configuration conf) {
    mysqlInit = new MysqlInit(dataSource)
    hbaseInit = new HbaseInit(conf)
    initBaseDir()
  }

  void initBaseDir() {
    baseDir = PathUtil.getBaseDir()
  }

  @Override
  void run() {
    init()
  }

  private void init() {
    try {
      List<Data> list = new LinkedList<>()
      //todo 目录名称之后改成可配置
      new File(baseDir, "data").eachFile({ File file ->
        if (file.isDirectory()) {
          new File(file.path).eachFile { File f ->
            if (f.isFile()) {
              logger.info(f.path)
              list.add FileProcess.dealWithFile(f.path)
            } else {
              logger.error("大哥 两层还不够您老折腾么 小爷不伺候了 gg")
            }
          }
        } else {
          logger.info(file.path)
          list.add FileProcess.dealWithFile(file.path)
        }
        for (Data data : list) {
          if (data.type == "mysql") {
            mysqlInit.init(data)
          } else {
            hbaseInit.init(data)
          }
        }
      })
    } catch (Exception e) {
      println e
    }
  }
}