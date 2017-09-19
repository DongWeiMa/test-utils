package com.dongweima.data.unit.test.db.init

import com.dongweima.data.unit.test.db.bean.ColumnMeta
import com.dongweima.data.unit.test.db.bean.Data
import com.dongweima.utils.db.hbase.HbaseBase
import org.apache.hadoop.conf.Configuration
import org.junit.Test
import org.junit.Before
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import static org.mockito.Mockito.*

class HbaseInitTest {
  
  HbaseInit hbaseInit

  @Before
  void setUp() {
   
  }

  @Test
  void testInit() {
    hbaseInit.init(new Data(tableName: "tableName", columnMeta: new ColumnMeta()))
  }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme