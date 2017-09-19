package com.dongweima.data.unit.test.db.init

interface LineProcess<T> {
  T dealWithLine(String line)
}
