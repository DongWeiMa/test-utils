主要实现了
## 如何简化初始化mysql和hbase
mysql使用了h2
hbase使用了hbase mini集群
mysql初始化支持读sql文件和自定义的data文件
hbase支持读取data文件 并自动创建相关表

### data文件规则：
1. 文件名即表名
 2. 具体内容
hbase
第二行是 列和列簇的定义 语法： 列簇1（列1，   列2..）   ..
第三行 数据定义 语法：  rowkey              |列1      |列2 ...
```
hbase
base(ptRule,family,quality) org(schoolId,structId)
role:1          |every_day      |count         |   every_day_uv|             1|      schoolId1|        structId1|
role:2          |every_day      |count         |   every_day_uv|             1|      schoolId1|        structId2|
1               |every_day      |count         |   every_day_uv|             1|      schoolId1|        structId3|
2               |every_day      |count         |   every_day_uv|             1|      schoolId1|        structId4|
```
mysql
第二行是 列定义 语法：    列1，    列2....
第三行 数据定义           列1       |列2 ...
```
mysql
id,name
1          |every_day
2          |every_day
```
### 自动读取data目录下的mysql相关的数据文件和sql文件
sql文件优先于数据文件
hbase不会自动读取，只会在第一次启动hbase mini集群的时候自动读取
因为很多时候测试，并不需要启动hbase mini集群  并且集群启动要几十秒 浪味时间
mhysql和hbase只会初始化一次 多次调用没用
使用方式
```
H2Config.getH2Datasource()
H2Config.getSql()
HbaseConfig.start()

```
```
@Configuration
@Profile("test")
class TestConfig {
  @Bean(name = "quotaDataSource")
  @Qualifier("quotaDataSource")
  @Primary
  DataSource tagDataSource() {
    return  H2Config.getH2Datasource()
  }

  @Bean
  DataInitScript dataInitScript(){
    //hbase的启动太慢 还是手动启动吧
    DataInitScript dataInitScript = new DataInitScript()
    dataInitScript.initAll true
    return dataInitScript
  }
}
```
## spingboot 整合
```
@WebAppConfiguration
@ContextConfiguration(classes = [App.class], loader = SpringBootContextLoader.class)
class QuotaConditionDaoImplTest extends Specification {
  def setupSpec() {}
  def setup() {}
  def "test demo"(){
        given:
            A a = new A()
        when:
            String result = a.doMethod()
        then:
            result.equal(data)
        //expect:
        cleanup:
            //clean
        where:
            data    |_
            "data"  |_
            "data2" |_
   }
}

@SpringBootApplication
@ComponentScan(["com.XXX.core","com.dongweima.data.unit.test"])
class App  extends SpringBootServletInitializer {

  static void main(String[] args) {
    run(App.class, args)
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(App.class)
  }
}
```
