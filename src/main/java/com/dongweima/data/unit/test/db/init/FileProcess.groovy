package com.dongweima.data.unit.test.db.init

import com.dongweima.data.unit.test.db.bean.ColumnMeta
import com.dongweima.data.unit.test.db.bean.ColumnValue
import com.dongweima.data.unit.test.db.bean.Data
import com.dongweima.data.unit.test.db.util.PathUtil

class FileProcess {
  static String baseDir
  static LineProcess<ColumnValue> columnValueLineProcess = new ColumnValueLineProcess()
  static LineProcess<ColumnMeta> columnMetaLineProcess = new ColumnMetaLineProcess()
  static {
    baseDir = PathUtil.getBaseDir()
  }
  //todo 写的比较死板 待修改    单元测试比较难写 因为要去读文件 无法对文件进行mock  先暂时直接读文件 
  static Data dealWithFile(String filePath) {
    Data data = new Data()
    try {
      data.setTableName filePath.substring(filePath.lastIndexOf(File.separator) + 1, filePath.length())
      String[] s = new File(filePath).readLines()
      List<String> list = new LinkedList<>()
      if (s != null && s.size() > 0) {
        for (int i = 0; i < s.size(); i++) {
          if (!s[i].trim().startsWith("#") && !s[i].trim() != "") {
            list.add(s[i].trim())
          }
        }
      }
      data.setType list.first.trim()
      list.removeFirst()
      data.setColumnMeta(columnMetaLineProcess.dealWithLine(list.first.trim()))
      list.removeFirst()
      for (String line : list) {
        data.addColumnValue(columnValueLineProcess.dealWithLine(line.trim()))
      }
      return data
    } catch (FileNotFoundException fnfe) {
      fnfe.printStackTrace()
    } catch (IOException ioe) {
      ioe.printStackTrace()
    }
    return null
  }

  static Data dealFileWithResourcePath(String path) {
    dealWithFile new File(baseDir, path).path
  }
}
