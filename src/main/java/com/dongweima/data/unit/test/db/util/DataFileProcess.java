package com.dongweima.data.unit.test.db.util;

import com.dongweima.data.unit.test.db.bean.ColumnMeta;
import com.dongweima.data.unit.test.db.bean.ColumnValue;
import com.dongweima.data.unit.test.db.bean.Data;
import com.dongweima.utils.file.FileUtil;
import com.dongweima.utils.file.PathUtil;
import java.io.File;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataFileProcess implements FileProcess<Data> {

  private static Logger logger = LoggerFactory.getLogger(DataFileProcess.class);
  private LineProcess<ColumnValue> columnValueLineProcess = new ColumnValueLineProcess();
  private LineProcess<ColumnMeta> columnMetaLineProcess = new ColumnMetaLineProcess();

  //todo 写的比较死板 待修改    单元测试比较难写 因为要去读文件 无法对文件进行mock  先暂时直接读文件
  public Data readFile(String filePath) {
    Data data = new Data();
    try {
      String fileName = filePath
          .substring(filePath.lastIndexOf(File.separator) + 1, filePath.length());
      LinkedList<String> list = FileUtil.getLines(filePath);
      String type = list.getFirst().trim();
      list.removeFirst();
      data.setTableName(fileName);
      data.setType(type);
      data.setColumnMeta(columnMetaLineProcess.dealWithLine(list.getFirst().trim()));
      list.removeFirst();
      for (String line : list) {
        if (!line.trim().endsWith("|")) {
          line = line + "|";
        }
        int size = data.getColumnMetasSize();
        int length = ColumnValueLineProcess.split(line, "|").size();
        if (length < size) {
          StringBuilder lineBuilder = new StringBuilder(line);
          for (int i = length; i < size; i++) {
            lineBuilder.append("|");
          }
          line = lineBuilder.toString();
        }
        if (length > size) {
          while (length >= size) {
            line = line.substring(0, line.lastIndexOf("|"));
            size++;
          }
        }
        data.addColumnValue(columnValueLineProcess.dealWithLine(line.trim()));
      }

      return data;
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  public Data readFileWithResourcePath(String path) {
    return readFile(new File(PathUtil.getBaseDir(), path).getPath());
  }

}
