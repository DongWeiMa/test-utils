package com.dongweima.data.unit.test.db.suit

import com.dongweima.data.unit.test.db.init.HbaseInitTest
import com.dongweima.data.unit.test.db.init.MysqlInitTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite.class)
@Suite.SuiteClasses([HbaseInitTest.class, MysqlInitTest.class])
class InitSuit {
}
