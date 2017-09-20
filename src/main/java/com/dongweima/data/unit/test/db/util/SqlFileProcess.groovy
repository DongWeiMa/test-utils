package com.dongweima.data.unit.test.db.util

class SqlFileProcess implements FileProcess<List<String>> {

  private StringBuilder unCompeleteSql = new StringBuilder()
  private List<String> sqls = new LinkedList<>()

  @Override
  List<String> readFile(String path) {
    List<String> lines = FileUtil.getLines(path)
    for (String line : lines) {
      if (line != null || line != "") {
        addLine(line)
      }
    }
    return sqls
  }

  @Override
  List<String> readFileWithResourcePath(String path) {
    readFile new File(PathUtil.getBaseDir(), path).path
  }

  private void addLine(String line) {
    unCompeleteSql.append(line)
    if (line.contains(";")) {
      sqls.add(unCompeleteSql.toString())
      unCompeleteSql = new StringBuilder()
    }
  }
}
