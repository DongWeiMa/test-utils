package com.dongweima.data.unit.test.db.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class FileUtil {
  private FileUtil() {

  }
  private static Logger logger = LoggerFactory.getLogger(FileUtil.class)

  static LinkedList<String> getLines(String filePath) {
    String[] s = new File(filePath).readLines()
    List<String> list = new LinkedList<>()
    if (s != null && s.size() > 0) {
      for (int i = 0; i < s.size(); i++) {
        if (!s[i].trim().startsWith("#") && !s[i].trim() != "") {
          list.add(s[i].trim())
        }
      }
    }
    return list
  }
  //todo 目录名称之后改成可配置
  static List<String> getFilePaths() {
    List<String> list = new LinkedList<>()
    new File(PathUtil.getBaseDir(), "data").eachFile({ File file ->
      if (file.isDirectory()) {
        new File(file.path).eachFile { File f ->
          if (f.isFile()) {
            list.add(f.path)
          } else {
            logger.error("大哥 两层还不够您老折腾么 小爷不伺候了 gg")
          }
        }
      } else {
        list.add(file.path)
      }
    })
    return list
  }
}
