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
  private String[] start = {"/*", "<!--"};
  private String[] end = {"*/", "-->"};
  private boolean isDoc = false;

  @Override
  public List<String> readFile(String path) {
    List<String> lines = null;
    try {
      lines = FileUtil.getLines(path);
    } catch (Exception e) {
      logger.error("file read failed path:{}", path, e);
    }

    if (lines != null) {
      //支持更多的语法
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

  //上下文
//几种情况
//注释： 多行 单行 行内注释
//sql 单行 多行
// 注释类型
//   # 开头的单行注释
//   /*开头的多行注释
//   <!----> 的注释
  private void addLine(String line) {
    line = deleteSingleLineDoc(line);
    if (line == null || line.equals("")) {
      return;
    }
    if (line.contains("/**") || line.contains("<!--")) {
      isDoc = true;
    }
    if (isDoc && line.contains("*/")) {
      line = line.substring(line.indexOf("*/") + 2, line.length());
      unCompeleteSql = new StringBuilder();
      isDoc = false;
    }
    if (isDoc && line.contains("-->")) {
      line = line.substring(line.indexOf("-->") + 3, line.length());
      unCompeleteSql = new StringBuilder();
      isDoc = false;
    }
    unCompeleteSql.append(line);
    if (line.contains(";")) {
      if (!isDoc) {
        sqls.add(unCompeleteSql.toString());
      }
      unCompeleteSql = new StringBuilder();
      isDoc = false;
    }
  }

  private String deleteSingleLineDoc(String line) {
    if (line.startsWith("//") || line.startsWith("#")) {
      return null;
    }
    if (line.contains("//")) {
      line = line.substring(0, line.indexOf("//"));
    }
    if (line.contains("#")) {
      line = line.substring(0, line.indexOf("#"));
    }
    if (line.contains("/*") && line.contains("*/")) {
      //边界值
      line = line.substring(0, line.indexOf("/*")) + line
          .substring(line.indexOf("*/") + 2, line.length());
    }
    if (line.contains("<!--") && line.contains("-->")) {
      //边界值
      line = line.substring(0, line.indexOf("<!--")) + line
          .substring(line.indexOf("-->") + 3, line.length());
    }
    return line;
  }

  public static void main(String[] args) {
    SqlFileProcess sqlFileProcess = new SqlFileProcess();
    System.out.println(sqlFileProcess.deleteSingleLineDoc("#"));
    System.out.println(sqlFileProcess.deleteSingleLineDoc("dsadsa#dsa  "));
    System.out.println(sqlFileProcess.deleteSingleLineDoc("dsadsa#dsa"));
    System.out.println(sqlFileProcess.deleteSingleLineDoc("dsadsa//dsa  "));
    System.out.println(sqlFileProcess.deleteSingleLineDoc("dsadsa//dsa"));
    System.out.println(sqlFileProcess.deleteSingleLineDoc("/*dsa*/"));
    System.out.println(sqlFileProcess.deleteSingleLineDoc("dsadsa/*dsa*/   "));
    System.out.println(sqlFileProcess.deleteSingleLineDoc("dsadsa/*dsa*/"));
    System.out.println(sqlFileProcess.deleteSingleLineDoc("<!--dsa-->"));
    System.out.println(sqlFileProcess.deleteSingleLineDoc("  dsadsa <!--dsa-->  "));
    System.out.println(sqlFileProcess.deleteSingleLineDoc("  dsadsa <!--dsa-->"));
  }
}
