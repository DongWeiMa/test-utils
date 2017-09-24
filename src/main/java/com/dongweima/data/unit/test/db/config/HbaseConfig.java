package com.dongweima.data.unit.test.db.config;

import com.dongweima.data.unit.test.db.init.DataInitScript;
import com.dongweima.utils.file.FileUtil;
import com.dongweima.utils.file.PathUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseTestingUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class HbaseConfig {

  private static Logger logger = LoggerFactory.getLogger(HbaseConfig.class);

  @Bean
  public Configuration hbaseConfiguration() {
    return testUtil.getConfiguration();
  }

  private static int count = 0;
  private static final HBaseTestingUtility testUtil = new HBaseTestingUtility();

  static {
    //复制文件
    copy();
  }

  public static void start() {
    if (count == 0) {
      synchronized (HbaseConfig.class) {
        if (count == 0) {
          try {
            //todo 考虑将这个设置变量改造成在window下才设置 这个变量可配置
            //考虑引入进来 并且包含 linux和window都可以用的
            //String path = new FileReader(HbaseConfig.class.getResourceAsStream("/")).getPath();

            DataInitScript.initAll(false);
            System.setProperty("hadoop.home.dir", PathUtil.getBaseDir());
            count++;
            testUtil.startMiniCluster();
          } catch (Throwable throwable) {
            logger.error("hbase mini 集群启动失败", throwable);
          }
        }
      }
    }
  }

  private static void copy() {

    copyFileInJarToClasspath("/bin/hadoop1", "bin/hadoop");
    copyFileInJarToClasspath("/bin/hadoop1.cmd", "bin/hadoop.cmd");
    copyFileInJarToClasspath("/bin/hadoop1.dll", "bin/hadoop.dll");
    copyFileInJarToClasspath("/bin/hadoop1.exp", "bin/hadoop.exp");
    //copy failed
    //copyFileInJarToClasspath("/bin/hadoop1.lib", "bin/hadoop.lib");
    copyFileInJarToClasspath("/bin/hadoop1.pdb", "bin/hadoop.pdb");
    copyFileInJarToClasspath("/bin/rcc1", "bin/rcc.exe");
    copyFileInJarToClasspath("/bin/winutils1.pdb", "bin/winutils.pdb");
    copyFileInJarToClasspath("/bin/winutils1.exe", "bin/winutils.exe");
    String base = "org/apache/hadoop/io/nativeio/";
    copyFileInJarToClasspath("/" + base + "NativeIO$CachedUid1.class",
        base + "NativeIO$CachedUid.class");
    copyFileInJarToClasspath("/" + base + "NativeIO$POSIX$CachedName1.class",
        base + "NativeIO$POSIX$CachedName.class");
    copyFileInJarToClasspath("/" + base + "NativeIO$POSIX$CacheManipulator1.class",
        base + "NativeIO$POSIX$CacheManipulator.class");
    copyFileInJarToClasspath("/" + base + "NativeIO$POSIX$IdCache1.class",
        base + "NativeIO$POSIX$IdCache.class");
    copyFileInJarToClasspath("/" + base + "NativeIO$POSIX$NoMlockCacheManipulator1.class",
        base + "NativeIO$POSIX$NoMlockCacheManipulator.class");
    copyFileInJarToClasspath("/" + base + "NativeIO$POSIX$Stat1.class",
        base + "NativeIO$POSIX$Stat.class");
    copyFileInJarToClasspath("/" + base + "NativeIO$POSIX1.class", base + "NativeIO$POSIX.class");
    copyFileInJarToClasspath("/" + base + "NativeIO$Windows$AccessRight1.class",
        base + "NativeIO$Windows$AccessRight.class");
    copyFileInJarToClasspath("/" + base + "NativeIO$Windows1.class",
        base + "NativeIO$Windows.class");
    copyFileInJarToClasspath("/" + base + "NativeIO1.class", base + "NativeIO.class");

  }

  //复制 从jar中的bin目录复制到  项目的resource目录下
  @SuppressWarnings("all")
  private static void copyFileInJarToClasspath(String source, String dest) {
    try {
      FileUtil.copyFileInJarToClasspath(source, dest);
    } catch (Exception e) {
      logger.error("copy file failed", e);
    }
  }

  public static HBaseTestingUtility getTestUtil() {
    if (count == 0) {
      start();
    }
    return testUtil;
  }

}
