package com.dongweima.data.unit.test.db.util

import com.dongweima.data.unit.test.db.bean.ColumnMeta
import com.dongweima.data.unit.test.db.bean.ColumnValue
import com.dongweima.data.unit.test.db.bean.Data

class DataFileProcess implements FileProcess<Data> {

  LineProcess<ColumnValue> columnValueLineProcess = new ColumnValueLineProcess()
  LineProcess<ColumnMeta> columnMetaLineProcess = new ColumnMetaLineProcess()

  //todo 写的比较死板 待修改    单元测试比较难写 因为要去读文件 无法对文件进行mock  先暂时直接读文件 
  Data readFile(String filePath) {
    Data data = new Data()
    try {
      String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1, filePath.length())
      LinkedList<String> list = FileUtil.getLines(filePath)
      String type = list.first.trim()
      list.removeFirst()
      data.setTableName fileName
      data.setType type
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

  Data readFileWithResourcePath(String path) {
    readFile new File(PathUtil.getBaseDir(), path).path
  }

}
