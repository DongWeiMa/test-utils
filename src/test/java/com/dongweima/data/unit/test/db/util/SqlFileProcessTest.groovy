package com.dongweima.data.unit.test.db.util

import spock.lang.Specification

class SqlFileProcessTest extends Specification {

  private SqlFileProcess sqlFileProcess = new SqlFileProcess()

  def "test read File With Resource Path"() {
    when:
    List<String> result = sqlFileProcess.readFileWithResourcePath("data" + File.separator + "mysql" + File.separator + "schema.sql")

    then:
    result.size() == 1
  }
}

