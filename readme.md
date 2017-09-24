主要实现了
## 如何简化初始化mysql和hbase
mysql使用了h2 <br/>
hbase使用了hbase mini集群  <br/>
mysql初始化支持读sql文件（仅支持表创建，因为数据是由数据文件创建的）和自定义的data文件  <br/>
hbase支持读取data文件 并自动创建相关表（因为没有类型这一说） <br/>

### data文件规则：
1. 文件名即表名
2. 具体内容
hbase   <br/>
第二行是 列和列簇的定义 语法： 列簇1（列1，   列2..）   ..   <br/>
第三行 数据定义 语法：  rowkey              |列1      |列2 ...      <br/>
```
hbase
base(ptRule,family,quality) org(schoolId,structId)
role:1          |every_day      |              |               |             1|      schoolId1|        structId1|
role:2          |every_day      |count         |               |             1|      schoolId1|        structId2|
1               |every_day      |count         |   every_day_uv|             1|      schoolId1|        structId3|
2               |every_day      |count         |   every_day_uv|             1|      schoolId1|        structId4|
```
mysql     <br/>
第二行是 列定义 语法：    列1，    列2....       <br/>
第三行 数据定义           列1       |列2 ...       <br/>
```
mysql
id,name
1          |every_day
2          |every_day
```
### 自动读取data目录下的mysql相关的数据文件和sql文件
sql文件优先于数据文件  <br/>
hbase不会自动读取，只会在第一次启动hbase mini集群的时候自动读取   <br/>
因为很多时候测试，并不需要启动hbase mini集群 <br/>  
并且集群启动要几十秒 浪费时间  <br/>
mhysql和hbase只会初始化一次 多次调用没用   <br/>
使用方式
```
H2Config.getH2Datasource()   
H2Config.getSql()   //groovy 进行测试 反向校验使用sql时可以用到
HbaseConfig.start()  //启动hbase 
Data data = DataInitScript.initDataWithResourcePath("test/test") //读取其他目录下的数据文件
(data)

 

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
    dataInitScript.initAll true //跳过hbase初始化
    return dataInitScript
  }
}
```
## 解决hbase mini启动在windows下的问题
habse mini在window下启动主要有两个问题  <br/>
一个是依赖hadoop-common下的一些文件     <br/>
还有一个就是 window本地方法调用会有点小问题   <br/>
解决方法时找到相应的文件(NativeIO这个类要修改，并且编译成class文件)，放到jar的resource下，启动时自动复制到classpath下即可

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
