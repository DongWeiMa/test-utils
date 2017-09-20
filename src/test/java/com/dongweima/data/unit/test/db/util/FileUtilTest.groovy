package com.dongweima.data.unit.test.db.util

import spock.lang.Specification

import static org.junit.Assert.assertArrayEquals

class FileUtilTest extends Specification {

  def setup() {
  }

  def "test get Lines"() {
    given:
    String filePath = new File(PathUtil.getBaseDir(), "test" + File.separator + "file").path
    when:
    LinkedList<String> result = FileUtil.getLines(filePath)

    then:
    assertArrayEquals((String[]) result.toArray()) == (lines.toArray() as String[])
    where:
    lines      | _
    ["1", "2"] | _
  }

  void "get file paths under ./data but just verify the file number"() {
    when:
    List<String> paths = FileUtil.getFilePaths()
    then:
    paths.size() == 2
  }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme