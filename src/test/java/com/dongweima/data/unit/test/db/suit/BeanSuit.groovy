package com.dongweima.data.unit.test.db.suit

import com.dongweima.data.unit.test.db.bean.ColumnMetaTest
import com.dongweima.data.unit.test.db.bean.ColumnValueTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite.class)
@Suite.SuiteClasses([
  ColumnMetaTest.class,
  ColumnValueTest.class
])
class TestSuit {
}
