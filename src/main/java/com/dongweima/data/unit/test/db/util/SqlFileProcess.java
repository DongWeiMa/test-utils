package com.dongweima.data.unit.test.db.util;

import com.dongweima.utils.file.FileUtil;
import com.dongweima.utils.file.PathUtil;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlFileProcess implements FileProcess<List<String>> {

  private static Logger logger = LoggerFactory.getLogger(SqlFileProcess.class);

  private StringBuilder unCompeleteSql = new StringBuilder();
  private List<String> sqls = new LinkedList<String>();

  @Override
  public List<String> readFile(String path) {
    List<String> lines = null;
    try {
      lines = FileUtil.getLines(path);
    } catch (Exception e) {
      logger.error("file read failed path:{}", path, e);
    }

    if (lines != null) {
      for (String line : lines) {
        if (line != null && !"".equals(line)) {
          addLine(line);
        }
      }
    }
    return sqls;
  }

  @Override
  public List<String> readFileWithResourcePath(String path) {
    return readFile(new File(PathUtil.getBaseDir(), path).getPath());
  }

  private void addLine(String line) {
    unCompeleteSql.append(line);
    if (line.contains(";")) {
      sqls.add(unCompeleteSql.toString());
      unCompeleteSql = new StringBuilder();
    }
  }
}
