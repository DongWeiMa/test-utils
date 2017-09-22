package com.dongweima.data.unit.test.db.config;

import com.dongweima.utils.file.PathUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseTestingUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class HbaseConfig {

  private static Logger logger = LoggerFactory.getLogger(HbaseConfig.class);

  @Bean
  @Lazy
  public Configuration hbaseConfiguration() {
    return testUtil.getConfiguration();
  }

  private static int count = 0;
  private static final HBaseTestingUtility testUtil = new HBaseTestingUtility();

  public static void start() {
    if (count == 0) {
      synchronized (HbaseConfig.class) {
        if (count == 0) {
          try {
            //todo 考虑将这个设置变量改造成在window下才设置 这个变量可配置
            //考虑引入进来 并且包含 linux和window都可以用的
            //String path = new FileReader(HbaseConfig.class.getResourceAsStream("/")).getPath();
            //复制文件
            copy();
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

  private static void copy() throws IOException {
    copyFileUsingFileChannels("/bin/hadoop1", "bin/hadoop");
    copyFileUsingFileChannels("/bin/hadoop1.cmd", "bin/hadoop.cmd");
    copyFileUsingFileChannels("/bin/hadoop1.dll", "bin/hadoop.dll");
    copyFileUsingFileChannels("/bin/hadoop1.exp", "bin/hadoop.exp");
    copyFileUsingFileChannels("/bin/hadoop1.lib", "bin/hadoop.lib");
    copyFileUsingFileChannels("/bin/hadoop1.pdb", "bin/hadoop.pdb");
    copyFileUsingFileChannels("/bin/rcc1", "bin/rcc.exe");
    copyFileUsingFileChannels("/bin/winutils1.pdb", "bin/winutils.pdb");
    copyFileUsingFileChannels("/bin/winutils1.exe", "bin/winutils.exe");
  }

  //复制 从jar中的bin目录复制到  项目的resource目录下
  @SuppressWarnings("all")
  private static void copyFileUsingFileChannels(String source, String dest) throws IOException {
    InputStream in = null;
    FileOutputStream fo = null;
    File file = new File(PathUtil.getBaseDir(), dest);
    if (!file.exists()) {
      //创建目录以及文
      File bin = new File(PathUtil.getBaseDir(), "bin");
      if (!bin.exists()) {
        bin.mkdir();
      }
      File f = new File(PathUtil.getBaseDir(), dest);
      f.createNewFile();
      try {
        in = HbaseConfig.class.getResourceAsStream(source);
        fo = new FileOutputStream(f);
        int i;
        while ((i = in.read()) != -1) {
          fo.write(i);
        }
      } catch (Throwable e) {
        logger.error("copy failed", e);
        f.delete();
        bin.delete();
      } finally {
        if (in != null) {
          try {
            in.close();
          } catch (Exception e) {
            logger.error("close failed", e);
          }
        }
        if (fo != null) {
          try {
            fo.close();
          } catch (Exception e) {
            logger.error("close failed", e);
          }
        }
      }
    }

  }

  public static HBaseTestingUtility getTestUtil() {
    if (count == 0) {
      start();
    }
    return testUtil;
  }

}
