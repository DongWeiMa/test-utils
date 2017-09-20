package com.dongweima.data.unit.test.db.util

class PathUtil {
  private PathUtil() {

  }
  static String baseDir
  static String getBaseDir(){
    if (baseDir == null) {
      baseDir = PathUtil.class.getResource("/")
      baseDir = baseDir.substring(5, baseDir.length())
    }
    return baseDir
  }
}
