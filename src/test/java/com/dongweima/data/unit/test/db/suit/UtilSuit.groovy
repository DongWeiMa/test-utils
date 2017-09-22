package com.dongweima.data.unit.test.db.suit

import com.dongweima.data.unit.test.db.util.*
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite.class)
@Suite.SuiteClasses([
  ColumnMetaLineProcessTest.class,
  ColumnValueLineProcessTest.class,
  DataFileProcessTest.class,
  SqlFileProcessTest.class,
  SqlUtilTest.class
])
class UtilSuit {
}
