package com.dongweima.data.unit.test.db.util

class PathUtil {
  static String getBaseDir(){
    String baseDir = PathUtil.class.getResource("/")
    baseDir = baseDir.substring(5, baseDir.length())
  }
}
