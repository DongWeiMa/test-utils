package com.dongweima.data.unit.test.db.suit

import com.dongweima.data.unit.test.db.bean.ColumnMetaTest
import com.dongweima.data.unit.test.db.bean.ColumnValueTest
import com.dongweima.data.unit.test.db.bean.DataTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite.class)
@Suite.SuiteClasses([
  ColumnMetaTest.class,
  ColumnValueTest.class,
  DataTest.class
])
class BeanSuit {
}
