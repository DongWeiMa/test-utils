package com.dongweima.data.unit.test.db.hbase;

import com.dongweima.data.unit.test.db.config.HbaseConfig;
import com.dongweima.utils.db.hbase.Family;
import com.dongweima.utils.db.hbase.HbaseObj;
import com.dongweima.utils.db.hbase.Qualifier;
import com.dongweima.utils.db.hbase.Row;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HbaseTestUtil {

  private static final Logger log = LoggerFactory
      .getLogger(com.dongweima.utils.db.hbase.HbaseBase.class);

  public static void dropTable(String tableName) {
    TableName tn = TableName.valueOf(tableName);

    Connection connection = getConnection();
    Admin admin = null;
    try {
      admin = connection.getAdmin();
      if (admin.tableExists(TableName.valueOf(tableName))) {
        try {
          admin.disableTable(tn);
        } catch (Exception e) {
          log.info("disableTable failed", e);
        }
        admin.deleteTable(tn);
      }
    } catch (Throwable t) {
      log.error("drop table failed", t);
    } finally {
      close(connection, admin);
    }
  }

  public static void createTable(String tableName, String[] families) {
    TableName tn = TableName.valueOf(tableName);
    Connection connection = getConnection();
    Admin admin = null;
    try {
      admin = connection.getAdmin();
      if (!admin.tableExists(tn)) {
        HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
        if (families != null) {
          for (String family : families) {
            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(family);
            tableDescriptor.addFamily(hColumnDescriptor);
          }
        }
        admin.createTable(tableDescriptor);
      }
    } catch (Throwable t) {
      log.error("create table failed", t);
    } finally {
      close(connection, admin);
    }
  }

  public static boolean tableExist(String tableName) {
    Connection connection = getConnection();
    Admin admin = null;
    boolean flag = false;
    try {
      admin = connection.getAdmin();
      TableName tn = TableName.valueOf(tableName);
      flag = admin.tableExists(tn);
    } catch (Throwable e) {
      log.warn("table exist has error");
    } finally {
      close(connection, admin);
    }
    return flag;
  }

  public static boolean namespaceExist(String namespace) {
    Connection connection = getConnection();
    Admin admin = null;
    boolean flag = false;
    try {
      admin = connection.getAdmin();
      NamespaceDescriptor nd = admin.getNamespaceDescriptor(namespace);
      if (nd != null) {
        flag = true;
      }
    } catch (Throwable e) {
      log.warn("table exist has error");
    } finally {
      close(connection, admin);
    }
    return flag;
  }

  public static void put(String tableName, Row row) {
    Table table = null;
    Connection connection = null;
    try {
      connection = getConnection();
      TableName tn = TableName.valueOf(tableName);
      table = connection.getTable(tn);
      Put put = new Put(row.getRowKey().getBytes());
      for (Family family : row.getFamilies()) {
        for (Qualifier qualifier : family.getQualifiers()) {
          put.addColumn(family.getName().getBytes(), qualifier.getName().getBytes(),
              qualifier.getValue().getBytes());
        }
      }
      table.put(put);
    } catch (Exception e) {
      log.error("", e);
    } finally {
      close(connection, table);
    }

  }

  public static List<Row> scan(HbaseObj obj) {
    Table table = null;
    ResultScanner results = null;
    Scan scan = new Scan();
    List<Row> list = new LinkedList<Row>();
    Long startTime = System.currentTimeMillis();
    scan.setReversed(obj.getIsReversed());
    if (obj.getStartRow() != null) {
      scan.setStartRow(obj.getStartRow().getBytes());
    }
    if (obj.getEndRow() != null) {
      scan.setStopRow(obj.getEndRow().getBytes());
    }

    scan.setFilter(obj.getFilter());
    for (Family family : obj.getFamilies()) {
      scan.addFamily(family.getName().getBytes());
      for (Qualifier qulifier : family.getQualifiers()) {
        scan.addColumn(family.getName().getBytes(), qulifier.getName().getBytes());
      }
    }
    scan.setCaching(100);//告诉扫描器最大缓存行数
    //scan.setBatch(100);每行最多的列数
    scan.setMaxVersions(1);
    Connection connection = getConnection();
    TableName tableName = TableName.valueOf(obj.getTableName());
    try {
      table = connection.getTable(tableName);
      results = table.getScanner(scan);
      for (Result result : results) {
        list.add(resultToRow(result));
      }
      log.debug("\nthis scan spent {} \n the condition is:{}",
          System.currentTimeMillis() - startTime, obj.toString());
    } catch (Exception e) {
      log.error("scan 失败", e);
    } finally {
      close(connection, table, results);
    }
    return list;
  }

  public static List<Row> simpleScan(String tableName, String startRow, String endRow) {
    Table table = null;
    ResultScanner results = null;
    Scan scan = new Scan();
    List<Row> list = new LinkedList<Row>();
    scan.setStartRow(startRow.getBytes());
    if (endRow != null) {
      scan.setStopRow(endRow.getBytes());
    }
    scan.setCaching(100);//告诉扫描器最大缓存行数
    //scan.setBatch(100);每行最多的列数
    scan.setMaxVersions(1);
    Connection connection = getConnection();
    try {
      table = connection.getTable(TableName.valueOf(tableName));
      results = table.getScanner(scan);
      for (Result result : results) {
        list.add(resultToRow(result));
      }
    } catch (Exception e) {
      log.error("scan 失败", e);
    } finally {
      close(connection, table, results);
    }
    return list;
  }

  public static List<Row> simpleScan(String tableName, String startRow) {
    return simpleScan(tableName, startRow, null);
  }

  //todo 判断namespace是否存在
  public static void createNameSpace(String name) {
    if (namespaceExist(name)) {
      return;
    }
    Connection connection = getConnection();
    Admin admin = null;
    try {
      admin = connection.getAdmin();
      NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(name).build();
      admin.createNamespace(namespaceDescriptor);
    } catch (Exception e) {
      log.error("创建命令空间{}失败", name, e);
    } finally {
      close(connection, admin);
    }
  }

  private static Row resultToRow(Result result) {
    Row row = new Row();
    row.setRowKey(Bytes.toString(result.getRow()));
    NavigableMap<byte[], NavigableMap<byte[], byte[]>> map = result.getNoVersionMap();
    for (byte[] a : map.keySet()) {
      String family = Bytes.toString(a);
      NavigableMap<byte[], byte[]> map2 = map.get(a);
      for (byte[] b : map2.keySet()) {
        String qualifier = Bytes.toString(b);
        String value = Bytes.toString(map2.get(b));
        row.setCellValue(family, qualifier, value);
      }
    }
    return row;
  }

  private static Connection getConnection() {
    try {
      Connection connection = ConnectionFactory.createConnection(getConf());
      return connection;
    } catch (Exception e) {
      log.error("获取连接失败", e);
    }
    return null;
  }

  private static void close(Connection connection, Table table, ResultScanner results) {
    close(results);
    close(table);
    close(connection);
  }

  private static void close(Connection connection, Admin admin, Table table) {
    close(table);
    close(admin);
    close(connection);

  }

  private static void close(Connection connection, Admin admin) {
    close(admin);
    close(connection);
  }

  private static void close(Connection connection, Table table) {
    close(table);
    close(connection);
  }

  private static void close(Connection connection) {
    if (connection != null) {
      try {
        connection.close();
      } catch (Throwable throwable) {
        log.error("hbase连接关闭失败", throwable);
      }
    }
  }

  private static void close(ResultScanner results) {
    if (results != null) {
      try {
        results.close();
      } catch (Exception e) {
        log.error("ResulScanner 关闭失败", e);
      }
    }
  }

  private static void close(Table table) {
    if (table != null) {
      try {
        table.close();
      } catch (Throwable e) {
        log.error("table 关闭失败", e);
      }
    }
  }

  private static void close(Admin admin) {
    if (admin != null) {
      try {
        admin.close();
      } catch (Throwable throwable) {
        log.error("admin close failed", throwable);
      }
    }
  }

  public static Configuration getConf() {
    return HbaseConfig.getTestUtil().getConfiguration();
  }
}
