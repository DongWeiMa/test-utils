package com.dongweima.data.unit.test.db.init;

import com.dongweima.data.unit.test.db.bean.Data;
import com.dongweima.data.unit.test.db.util.DataFileProcess;
import com.dongweima.data.unit.test.db.util.FileProcess;
import com.dongweima.data.unit.test.db.util.SqlFileProcess;
import com.dongweima.utils.file.FileUtil;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataInitScript {

  private static Logger logger = LoggerFactory.getLogger(DataInitScript.class);
  private static FileProcess<List<String>> sqlFileProcess = new SqlFileProcess();
  private static FileProcess<Data> dataFileProcess = new DataFileProcess();
  private static Init mysqlInit = new MysqlInit();
  private static Init hbaseInit = new HbaseInit();
  private static int count = 0;
  private static int mysql = 0;

  static void initAll(boolean skipHbsae) {
    if (count == 0) {
      synchronized (DataInitScript.class) {
        if (count == 0) {
          if (!skipHbsae) {
            count = 1;
          }
          List<String> fps = sort(FileUtil.getFilePaths());
          try {
            for (String filePath : fps) {
              if (filePath.endsWith(".sql")) {
                List<String> sqls = sqlFileProcess.readFile(filePath);
                if (mysql == 0) {
                  ((MysqlInit) mysqlInit).init(sqls);
                }
              } else {
                Data data = dataFileProcess.readFile(filePath);
                if (data.getType().equals("mysql")) {
                  if (mysql == 0) {
                    mysqlInit.init(data);
                  }
                } else {
                  if (!skipHbsae) {
                    hbaseInit.init(data);
                  }
                }
              }
            }
          } catch (Exception e) {
            logger.error(e.getMessage(), e);
          }
          mysql = 1;
        }
      }
    }
  }

  static Data getData(String fileName) {
    return dataFileProcess.readFileWithResourcePath(fileName);
  }

  /**
   * sql 文件优先执行
   */
  private static List<String> sort(List<String> list) {
    List<String> s = new LinkedList<String>();
    for (String f : list) {
      if (f.endsWith(".sql")) {
        s.add(f);
      }
    }
    for (String f : list) {
      if (!f.endsWith(".sql")) {
        s.add(f);
      }
    }
    return s;
  }
}
