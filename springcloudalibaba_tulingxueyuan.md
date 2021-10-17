# springcloudalibaba_tulingxueyuan



## 1.项目创建与修改

父项目可采用spring初始化向导进行创建

子项目通过maven进行模块化创建

parent/pom.xml

```xml

<dependencyManagement>
    <dependencies>
	    <!--springcloudalibaba的版本管理。通过dependency完成继承-->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-alibaba-dependencies</artifactId>
            <version>2.2.5.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <!--springboot的版本管理。通过dependency完成继承-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-parent</artifactId>
            <version>2.3.7.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
          <!--springcloud的版本管理。-->
          <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-dependencies-parent</artifactId>
              <version>Hoxton.SR8</version>
              <type>pom</type>
              <scope>import</scope>
          </dependency>
    </dependencies>
</dependencyManagement>
```



版本统一管理parent/pom.xml

```xml
<properties>
    <java.version>1.8</java.version>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>

    <spring-boot.version>2.3.7.RELEASE</spring-boot.version>
    <spring.cloud.alibaba.version>2.2.5.RELEASE</spring.cloud.alibaba.version>
    <spring.cloud.version>Hoxton.SR8</spring.cloud.version>
</properties>
```

```xml
<dependencyManagement>
    <dependencies>
        <!--springcloudalibaba的版本管理。通过dependency完成继承-->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-alibaba-dependencies</artifactId>
            <version>${spring.cloud.alibaba.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>

        <!--springboot的版本管理。通过dependency完成继承-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-parent</artifactId>
            <version>${spring-boot.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>

        <!--springcloud的版本管理。-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring.cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```



## 2.分布式体验

子项目1order/pom.xml、子项目2store/pom.xml

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

子项目1配置

```
server:
  port: 8010
```

配置文件

```java
@Bean
public RestTemplate restTemplate(RestTemplateBuilder Builder){
    RestTemplate build = Builder.build();
    return build;

}
```

controller.java

```java
/**
 * @Author
 * @date 2021年10月04日13:40
 */
@RestController
@RequestMapping("/order")
public class ordercontroller {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/add")
    public String add(){
        System.out.println("调用成功");
        String msg = restTemplate.getForObject("http://localhost:8011/stock/reduct", String.class);
        return "helloworld"+msg;
    }
}
```

子项目2配置

```yml
server:
  port: 8011
```

controller.java

```java
/**
 * @Author
 * @date 2021年10月04日13:42
 */
@RequestMapping("/stock")
@RestController
public class sotre {
    @RequestMapping("/reduct")
    public String reduct(){
        System.out.println("扣减成功");
        return "springcloudalibaba";
    }
}
```

## 3.Alibaba微服务组件Nacos注册中心

集注册中心‘配置中心服务管理的平台

Nacos的关键特性包括：

- 服务发现和服务健康监测
- 动态配置服务
- 动态DNS配置
- 服务及其2元数据管理

#### 核心功能

服务注册：

服务心跳：

服务同步：

服务发现：

服务健康监测：

#### 主流的注册中心：

CAP：C一致性A可用性P分区容错性

NACOS：CP+AP

Eureka:  AP

Consul: CP

CoreDNS: --

Zookeeper:CP



#### Nacos配置

默认是集群模式

windows模式下修改为单机模式

右键nacos/bin/startup.cmd编辑它

set MODE="cluster"  //默认为集群模式
set FUNCTION_MODE="all"
set SERVER=nacos-server
set MODE_INDEX=-1
set FUNCTION_MODE_INDEX=-1
set SERVER_INDEX=-1
set EMBEDDED_STORAGE_INDEX=-1
set EMBEDDED_STORAGE=""

//以上配置运行这里

if %MODE% == "cluster" (
    echo "nacos is starting with cluster"
	  if %EMBEDDED_STORAGE% == "embedded" (
	      set "NACOS_OPTS=-DembeddedStorage=true"
	  )

set "NACOS_JVM_OPTS=-server -Xms2g -Xmx2g -Xmn1g -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m -XX:-OmitStackTraceInFastThrow -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=%BASE_DIR%\logs\java_heapdump.hprof -XX:-UseLargePages"

)

改

set MODE="standalone"//改为单机模式
set FUNCTION_MODE="all"
set SERVER=nacos-server
set MODE_INDEX=-1
set FUNCTION_MODE_INDEX=-1
set SERVER_INDEX=-1
set EMBEDDED_STORAGE_INDEX=-1
set EMBEDDED_STORAGE=""

使之运行这里

if %MODE% == "standalone" (
    echo "nacos is starting with standalone"
	  set "NACOS_OPTS=-Dnacos.standalone=true"
    set "NACOS_JVM_OPTS=-Xms512m -Xmx512m -Xmn256m"
)



##### 全局配置文件：

application.properties



双击startup.cmd启动

![image-20211004160004230](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004160004230.png)

直到打印出success

复制http://192.168.72.1:8848/nacos/index.html

到浏览器进入管理界面

账户密码默认nacos

![image-20211004155909049](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004155909049.png)



## 4.NacosClient搭建

![image-20211004160526936](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004160526936.png)

cv出两个子项目重命名为order-nacos以及store-nacos，本节在这两个子项目进行操作

![image-20211004160629879](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004160629879.png)

分别修改两个子工程的pom.xml的<artifactId>标签内容为工程名



在父工程pom.xml中添加这两个模块（因为是复制进来的他不会自动给我们加上这两个模块）

![image-20211004160814389](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004160814389.png)

刷新maven面板

![image-20211004161003388](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004161003388.png)

删除非本子工程的iml文件（另一个同理）

![image-20211004161124972](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004161124972.png)



如果刷新mavne以后还是灰色的，是被idea排除掉了，setting->Build,Execution->Build Tools->Maven->ignored files展开将这两个勾去掉

![image-20211004161346730](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004161346730.png)

![image-20211004161603666](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004161603666.png)

在两个子项目中添加依赖

```
<!--nacos-服务注册-->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
```



可以确认依赖有没有加进来

![image-20211004162136620](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004162136620.png)



修改配置文件applicatiom.yml

```yml
#应用名称(nacos会将该名称当做服务名称)
spring:
  application:
    name: order-service
  cloud:
    nacos:
      discovery:
        username: nacos
        password: nacos
        namespace: public #命名空间 管理界面的服务列表
      server-addr: localhost:8848
```

```yml
#应用名称(nacos会将该名称当做服务名称)
spring:
  application:
    name: store-service
  cloud:
    nacos:
      discovery:
        username: nacos
        password: nacos
        namespace: public #命名空间 管理界面的服务列表
      server-addr: localhost:8848
```



启动服务并修改配置名称

![image-20211004163337030](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004163337030.png)

![image-20211004163409871](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004163409871.png)

另外一个同理

#### 服务注册：

![image-20211004163524325](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004163524325.png)

将当前服务注册到nacos中心



刷新服务；列表

![image-20211004163619568](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004163619568.png)

详情页面

![image-20211004163741331](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004163741331.png)

停止其中一个服务

![image-20211004163934518](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004163934518.png)

等个15s刷新网页

服务停止触发保护状态

![image-20211004164059695](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004164059695.png)

过个20-30s消失此条记录（无心跳时间过长剔除服务）

#### 服务调用

将服务名复制到此处

```java
@RequestMapping("/add")
public String add(){
    System.out.println("调用成功");
    String msg = restTemplate.getForObject("http://store-service/stock/reduct", String.class);
    return "helloworld"+msg;
}
```

重启两个子项目

访问该资源

![image-20211004182712800](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004182712800.png)

![image-20211004182756996](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004182756996.png)



此处需要依靠负载均衡器解析服务名称，然后才能调用服务地址

添加负载均衡注解

```java
@Bean
@LoadBalanced//添加负载均衡注解
public RestTemplate restTemplate(RestTemplateBuilder Builder){
    RestTemplate build = Builder.build();
    return build;

}
```

相当于RestTemplate拥有负载均衡机制

重新刷新请求地址

![image-20211004183248688](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004183248688.png)

![image-20211004183518847](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004183518847.png)



默认采用轮询的负载均衡机制

演示：

![image-20211004183715650](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004183715650.png)

![image-20211004183905578](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004183905578.png)



store.java注入端口

```java
@Value("${server.port}")
String port;

```

将其输出

```java
@RequestMapping("/reduct")
public String reduct(){
    System.out.println("扣减成功");
    return "springcloudalibaba"+port;
}
```

重启8021的服务

启动8022的服务

刷新页面

![image-20211004184303382](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004184303382.png)

![image-20211004184319089](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004184319089.png)

雪崩保护：

​	保护阈值：设置0-1之间的值

​	临时实例: spring-cloud-nacos-discovery.ephemeral=false:当服务宕机了也不会从服务列表中剔除

![image-20211004203851290](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004203851290.png)

yml配置

![image-20211004203912022](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004203912022.png)

永久实例

![image-20211004204328069](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004204328069.png)



停止8022的服务

![image-20211004204434374](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004204434374.png)

等个20-30s

![image-20211004204516658](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004204516658.png)

永久存在健康与不健康的实例



健康实例、不健康实例：

健康实例数/总实例数<保护阈值（1/2=0.5<0.6）此时依然会将不健康的实例拿给你用防止洪峰流量到来时全体服务宕机。（挂掉的服务的页面也会被调出来）



权重：

权重值越大分配到的流量就越大

下线：

下线后会调用不到服务



服务名：二选一配置

![image-20211004213144953](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004213144953.png)



nacos的一系列配置

![image-20211004214056883](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004214056883.png)



## Nacos集群搭建

环境

jdk1.8+

maven3.3+

ngnix

mysql5

![image-20211004221049792](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004221049792.png)

上传linux并解压，复制多份



以8849为例子



修改application,properties的配置，使用外置数据源，要使用（含）mysql5.7+

```
#*************** Config Module Related Configurations ***************#

###If use MySQL as datasource:

spring.datasource.platform=mysql

###Count of DB:

db.num=1

###Connect URL of DB:

db.url.0=jdbc:mysql://127.0.0.1:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC

db.user.0=nacos

db.password.0=nacos

###Connection pool configuration: hikariCP

db.pool.config.connectionTimeout=30000
db.pool.config.validationTimeout=10000
db.pool.config.maximumPoolSize=20
db.pool.config.minimumIdle=2
```

![image-20211004233043089](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211004233043089.png)



将conf/cluster.conf.example改为cluster.conf，添加节点配置

```
#example
#IP:port
192.168.16.101:8847
192.168.16.102:8848
192.168.16.103:8849
```

修改bin/startup.sh（将内存改小）

```
#===========================================================================================

# JVM Configuration

#===========================================================================================
if [[ "${MODE}" == "standalone" ]]; then
    JAVA_OPT="${JAVA_OPT} -Xms512m -Xmx512m -Xmn256m"
    JAVA_OPT="${JAVA_OPT} -Dnacos.standalone=true"
else
    if [[ "${EMBEDDED_STORAGE}" == "embedded" ]]; then
        JAVA_OPT="${JAVA_OPT} -DembeddedStorage=true"
    fi
    JAVA_OPT="${JAVA_OPT} -server -Xms2g -Xmx2g -Xmn1g -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m"
    JAVA_OPT="${JAVA_OPT} -XX:-OmitStackTraceInFastThrow -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${BASE_DIR}/logs/java_heapdump.hprof"
    JAVA_OPT="${JAVA_OPT} -XX:-UseLargePages"

fi
```

修改此处

```

JAVA_OPT="${JAVA_OPT} -server -Xms2g -Xmx2g -Xmn1g -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m"
```

ngnix反向代理

```
upstream nacoscluster{

​	service 127.0.0.1:8050

​	service 127.0.0.1:8051

​   service 127.0.0.1:8049

}
```



创建mysql数据库（数据库名称要与指向的数据库名称一致），sql文件位置conf/nacos-mysql.sql

![image-20211005140100828](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005140100828.png)

![image-20211005140250583](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005140250583.png)

![image-20211005141042142](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005141042142.png)

拷贝一份文件cluster.conf搭建伪集群

![image-20211005141131938](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005141131938.png)

去编辑他

![image-20211005142404317](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005142404317.png)

下面修改第三个文件

![image-20211005142541753](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005142541753.png)

将其内存改小，如果你的虚拟机内存足够大可以不修改

![image-20211005142746948](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005142746948.png)

启动并查看日志文件

![image-20211005143009227](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005143009227.png)

![image-20211005143027380](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005143027380.png)

浏览器验证

![image-20211005143122969](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005143122969.png)

集群管理中的结点已经生效

![image-20211005143304362](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005143304362.png)

将8849的三个文件分别复制到8850/8851中对应的文件夹

![image-20211005143740986](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005143740986.png)

分别修改88508851的配置文件的端口

![image-20211005143931482](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005143931482.png)

![image-20211005143903520](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005143903520.png)

分别启动两个服务

![image-20211005144116807](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005144116807.png)

通过网页查看结点全部启动成功（或者分别访问三个站点）

![image-20211005144517531](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005144517531.png)

配置ngnix

![image-20211005145252347](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005145252347.png)

修改nginx配置文件

![image-20211005150407273](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005150407273.png)

在http下加上这个

![image-20211005152712686](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005152712686.png)

此时访问的ngnix:

http://192.168.1.129:8847/nacos/会进行反向代理，进行转发到配置中的代理地址：http://nacoscluster/nacos/ 该地址指向了upstream中的集群（默认采用轮询的负载均衡机制）

启动ngnix

![image-20211005152730320](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005152730320.png)

访问这个前置http://192.168.1.129:8847/nacos/反向代理到了nacos

![image-20211005152947830](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005152947830.png)



回到idea项目中将注册中心的地址给他

改成ngnix的地址192.168.1.129:8847

![image-20211005153347660](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005153347660.png)

![image-20211005153421160](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005153421160.png)

记得将这个注释掉或者改为临时实力，否则启动会报错

![image-20211005175629789](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005175629789.png)

集群相关文件

8848 8849 8850 等的访问记录（心跳）

![image-20211005180415893](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005180415893.png)

## Ribbon介绍

### 微服务负载均衡器

#### 常见负载均衡算法

- 随机：通过随机选择服务进行执行，一般这种方法用的比较少
- 轮训：负载均衡默认的实现方式，请求来之后排队处理
- 加权轮训：通过服务器性能的分型，给高配置低负载的服务器分配更高的权重，均衡各个服务器的压力
- 地址hash：通过客户端请求地址的HASH值取模映射进行服务器的调调度 ip hash
- 最小链接数：即使请求均衡了，压力不一定均衡，最小链接数法就是根据服务器的情况，比如请求和积压数等参数，将请求分配到当前压力最下的服务器上，最小活跃数

#### 负载均衡策略

#### 修改默认的负载均衡策略

##### 基于配置类

复制出一个服务消费方

![image-20211005222812356](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005222812356.png)

![image-20211005222929925](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005222929925.png)

修改artifactId

![image-20211005223035132](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005223035132.png)

在父项目中引入

![image-20211005223124807](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005223124807.png)



刷新maven

![image-20211005223240168](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211005223240168.png)

配置一个随机的负载均衡策略（路径要放在启动类的父级，避免被扫描到）

![image-20211006001010088](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006001010088.png)

并在启动类加上相应注解指定服务提供商

![image-20211006001639930](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006001639930.png)

修改端口启动项目

![image-20211006001729292](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006001729292.png)

将store的两个端口启动起来

![image-20211006001905106](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006001905106.png)



![image-20211006002023403](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006002023403.png)

刷新

![image-20211006002050346](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006002050346.png)

端口在8021/8022之间轮训切换

##### 基于配置文件

先将注解注释掉，免得跟配置文件冲突

![image-20211006002451357](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006002451357.png)

修改配置文件，添加该内容

![image-20211006002954612](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006002954612.png)

重启order-ribbon

打开服务列表修改权重

![image-20211006003314626](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006003314626.png)

![image-20211006003342144](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006003342144.png)



权重越大，得到的流量越高，但非概率性

多次刷新网页，22流量大于21，可测概率-（apiport.postman）-

![image-20211006003542796](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006003542796.png)

### 自定义负载均衡策略

定义一个类去继承AbstractLoadBalancerRule并实现两个方法

![image-20211006004739612](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006004739612.png)

```java
    @Override
    public Server choose(Object o) {

        ILoadBalancer loadBalancer = this.getLoadBalancer();
        //获得当前请求的实例
        List<Server> reachableServers = loadBalancer.getReachableServers();
        //使用线程随机数，传一个范围进去-->服务的数量，目前是两个（0/1）
        int random = ThreadLocalRandom.current().nextInt(reachableServers.size());
        //获取随机数下标
        Server server = reachableServers.get(random);
        /*if (server.isAlive()){
            return server;//是否存活，存活才返回
        }*/
        return server;
    }
}
```

覆盖完整类路径

```yml
store-service: #服务提供商
  ribbon:
    NFLoadBalancerRuleClassName:
       com.ribbon.rule.CustomeRule
```

重启8030和另外两个store(8021/8022)

![image-20211006010059025](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006010059025.png)

![image-20211006010109874](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006010109874.png)

随机性出现两个端口



懒加载

```yml
ribbon:
  eager-load:
    enabled: true #开启ribbon懒加载
    clients: store-service #配置mall-user使用ribbon懒加载，多个逗号分割
```

### 微服务负载均衡器spring cloud LoadBalancer

官方出品

在order-nacos基础上复制一个出来，命名为order-loadbalancer

记得修改下面这里，以及在父工程引入

```
<artifactId>order-loadbalancer</artifactId>
```

排除ribbon

```yml
<!--nacos-服务注册-->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

添加loadbanlance依赖

```pom
<!--添加loadbanlancer依赖-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-loadbalancer</artifactId>
</dependency>
```

禁用ribbon

```
spring:
  application:
    name: order-service
  cloud:
	loadbalancer:
		  ribbon:
		    enabled: false
```

```
server:
  port: 8031
```

启动两个服务提供者

![image-20211006012650965](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006012650965.png)

![image-20211006013325142](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006013325142.png)

![image-20211006013333155](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006013333155.png)

## 微服务组件Feign

简介：声明式模板化http客户端-----声明在消费端

优势：

#### springcloudalibaba正本openFeign

复制一个order-nacos为order-feign

记得修改子工程以及父工程的pom.xml



引入依赖

```
<!--添加openfeign依赖-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

```java
/**
 * @Author
 * @date 2021年10月06日13:49
 */
/**
*@Author  
*@dat   
*2.添加feign接口和方法
*name 指定要调用rest接口所对应的服务名称
*path 指定调用rest接口所在的storecontroller所指定的RequestMapping
 * 如果没有RequestMapping就不写
*/
@FeignClient(name = "store-service",path = "/stock")
public interface storeFeignService {
    //声明需要调用的rest接口对应的方法--直接复制他
    @RequestMapping("/reduct")
    public String reduct();
    //声明feign接口就行啦，不需要写实现类

}
/*
store-nacos.store.java
@RequestMapping("/stock")
@RestController
public class sotre {

    @Value("${server.port}")
    String port;

    @RequestMapping("/reduct")
    public String reduct(){
        System.out.println("扣减成功");
        return "扣减库存"+port;
    }
}*/
```

改造main

```java
@EnableFeignClients
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);

    }

}
```

改造controller

```java
/**
 * @Author
 * @date 2021年10月04日13:40
 */
@RestController
@RequestMapping("/order")
public class ordercontroller {

    @Autowired
    storeFeignService storeFeignService;

    @RequestMapping("/add")
    public String add(){
        System.out.println("调用成功");
        String msg=storeFeignService.reduct();
        return "hello  feign  "+msg;
    }
}
```

修改端口为8040

在启动store的两个服务

![image-20211006141313632](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006141313632.png)

![image-20211006141546251](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006141546251.png)

![image-20211006141602332](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006141602332.png)

springcloudfeign的自定义配置以及使用

日志配置·

配置类--全局配置- 作用在所有的服务提供商里面

准备一个product提供商

```xml
server:
  port: 8023
#应用名称(nacos会将该名称当做服务名称)
spring:
  application:
    name: product-service
  cloud:
    nacos:
      discovery:
        username: nacos
        password: nacos
        #namespace: public #命名空间 管理界面的服务列表
        #ephemeral: false  #设置永久实例  哪怕宕机了也不会删除实例
        #service: ${spring.application.name}  默认取${spring.application.name}，也可以通过该选项配置
        #group: DEFAULT_GROUP   #默认值 更细的相同特征的服务进行归类分组管理
        #weight: 通常要结合 安装 权重的负载均衡策略，权重越高分配的流量就越大
        #metadata: version=1 #元数据 可以结合元数据做扩展
      server-addr:  192.168.1.129:8847
```

```java
/**
 * @Author
 * @date 2021年10月04日13:42
 */
@RequestMapping("/product")
@RestController
public class Productcontroller {

    @Value("${server.port}")
    String port;

    @RequestMapping("/{id}")
    public String get(@PathVariable Integer id){
        System.out.println("查询商品 : "+id);
        return "查询商品 "+id+":"+port;
    }
}
```

order-openfeign

配置类

```java
package com.tuilngxueyuan.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author
 * @date 2021年10月06日14:28
 * 全局配置 当使用configuration会将配置作用在所有的服务提供方
 * 局部配置 如果只想针对某一个或者某几个服务进行配置就不要加这个注解
 */
@Configuration
public class feignconfig {
    //配置日志输出级别
    @Bean
    public Logger.Level feignLonggerLevel(){
       return Logger.Level.FULL;
    }
}
```

接口

```
package com.tuilngxueyuan.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author
 * @date 2021年10月06日14:32
 */
@FeignClient(name = "product-service",path = "/product")
public interface ProductFeignService {
    @RequestMapping("/{id}")
    public String get(@PathVariable("id") Integer id);
}
/**
*@Author
*@date
 *
 * @RequestMapping("/product")
 * @RestController
 * public class Productcontroller {
 *
 *     @Value("${server.port}")
 *     String port;
 *
 *     @RequestMapping("/{id}")
 *     public String get(@PathVariable Integer id){
 *         System.out.println("查询商品 : "+id);
 *         return "查询商品 "+id+":"+port;
 *     }
 * }
*/
```

调用方法

```

@RestController
@RequestMapping("/order")
public class ordercontroller {

    @Autowired
    storeFeignService storeFeignService;
    @Autowired
    ProductFeignService productFeignService;
    @RequestMapping("/add")
    public String add(){
        System.out.println("调用成功");
        String msg=storeFeignService.reduct();
        String id=productFeignService.get(1);
        return "hello  feign  "+msg+"id:"+id;
    }
}
```

![image-20211006144320180](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006144320180.png)

但是日志斌没有输出，因为feign以debug级别输出

```yml
#springboot默认日志级别是info,feign的debug日志级别就不会输出
logging:
  level:
    com.tuilngxueyuan.feign: debug #指定包名进行配置
  #level: debug  #此处回对所有的日志级别进行配置，
```

![image-20211006144942094](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006144942094.png)

只针对局部生效

配置文件feignconfig.java的注解去掉

接口文件的注解改成这样子

```java
@FeignClient(name = "store-service",path = "/stock",configuration = feignconfig.class)
```

配置文件的方式针对product

```yml
#feign局部的配置
feign:
  client:
    config:
      #指定服务名
      pruduct-service:
        loggerLevel: BASIC 
```

![image-20211006150252740](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006150252740.png)

![image-20211006150303174](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006150303174.png)

![image-20211006150347402](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211006150347402.png)





##### feign契约配置

config/feignconfig.java

```java
/***
*@Author
*@date
 * 修改契约配置，支持feign原生的注解
*/
@Bean
public Contract feignController(){
    return new Contract.Default();
}
```

但是我们习惯性使用的是配置文件的方式去进行配置，所以该代码在项目中会被注释



配置文件形式通过Contract去配置

```yml
feign:
  client:
    config:
      #指定服务名
      pruduct-service:
        loggerLevel: BASIC
        contract: feign.Contract.Default # 设置为默认的契约，（还原成原生的注解）
```

ProductFeignService.java

将注解还原为feign原生注解

```java
@FeignClient(name = "product-service",path = "/product")
public interface ProductFeignService {
    @RequestLine("GET /{id}")
    public String get(@Param("id") Integer id);
}
```



##### feign超时时间配置

config/feignconfig.java

```java
/***
*@Author
*@date
 * 超时时间配置1
 *
*/
@Bean
public Request.Options options(){
    return new Request.Options(5000,10000);
}
```

或者配置文件的形式

```yml
#feign局部的配置
feign:
  client:
    config:
      #指定服务名
      pruduct-service:
        loggerLevel: BASIC
        #contract: feign.Contract.Default  # 设置为默认的契约，（还原成原生的注解）
        connectTimeout: 5000 #连接超时时间 默认2s
        readTimeout: 3000 #读取超时时间 默认5s
```

product-nacos.com.tuling.store.controller.Productcontroller.java

```java
@RequestMapping("/{id}")
public String get(@PathVariable Integer id) throws InterruptedException {
    Thread.sleep(4000);
    System.out.println("查询商品 : "+id);
    return "查询商品 "+id+":"+port;
}
```

让其消费者读取超时





自定义拦截器

```java
package com.tuilngxueyuan.intercptor.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Author
 * @date 2021年10月06日20:17
 */
public class CustomfeignInterceptor implements RequestInterceptor {
    Logger logger= LoggerFactory.getLogger(this.getClass());


    @Override
    public void apply(RequestTemplate requestTemplate) {
        logger.info("feign拦截器！");
    }
}
```

config.feignconfig.java

```java
/**
*@Author  
*@date    
*自定义拦截器
*/
     @Bean
    public  FeignAuthRequestInterceptor feignAuthRequestInterception(){
         return  new FeignAuthRequestInterceptor();
     }
```

配置文件形式

```yaml
feign:
  client:
    config:
      #指定服务名
      pruduct-service:
        loggerLevel: BASIC
        #contract: feign.Contract.Default  # 设置为默认的契约，（还原成原生的注解）
        connectTimeout: 5000 #连接超时时间 默认2s
        readTimeout: 3000 #读取超时时间 默认5s
        requestInterceptors[0]: 
          com.tulingxueyuan.intercptor.feign.CustomfeignInterceptor
```

—————————————————————————————————

## nacos配置中心

创建配置文件

#### ![image-20211007144409690](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007144409690.png)

内容会被创建在相应的数据库中的config.info

![image-20211007144520722](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007144520722.png)

创建完成就可以在微服务中进行引用

## Nacos-config Client读取配置

创建一个新的模块config-nacos

```java
package com.tuling.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author
 * @date 2021年10月07日15:06
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
```

yml

```yml
server:
  port: 8050
```

pom.xml

```xml
<!--nacos config依赖-->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
```

Application.java

```java
/**
 * @Author
 * @date 2021年10月07日15:06
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        String userName=applicationContext.getEnvironment().getProperty("user.name");
        String userAge=applicationContext.getEnvironment().getProperty("user.age");
        System.err.println("user name : "+userName+"; age: "+userAge);
    }
}
```

执行此example之前，必须使用bootstrap.properties配置文件来配置Nacos-Server地址

```yml
spring:
  application:
    name: config-nacos
  cloud:
    nacos:
      server-addr: 192.168.1.129:8847
```

启动系项目

![image-20211007152022848](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007152022848.png)

该username 为win系统用户名,并没有获取到配置文件中的属性

![image-20211007152750942](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007152750942.png)

![image-20211007153130608](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007153130608.png)

此处需要呼应上

```yml
spring:
  application:
    # 会自动根据服务名拉取对应的dataia对应的配置文件信息
    name: com.tuling.config.nacos
  cloud:
    nacos:
      server-addr: 192.168.1.129:8847
      username: nacos
      password: nacos
      config:
        namespace: public
```









权限管理（应该先修改配置文件）

![image-20211007155754220](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007155754220.png)

![image-20211007155824637](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007155824637.png)



![image-20211007155836022](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007155836022.png)

重新登录ali账户会发现权限被限制住了

![image-20211007155934375](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007155934375.png)

授权

![image-20211007155023817](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007155023817.png)

对集群中的机子打开权限管理

![image-20211007154806675](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007154806675.png)

----------------------------------------------------------------------------------------------

经过一顿操作，终于获得配置文件信息。太难了

![image-20211007161155696](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007161155696.png)

##### 当配置文件发生变化，代码是否能及时发现

改造代码

一秒钟赌一次的死循环

```java
public static void main(String[] args) throws InterruptedException {
    ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
   while (true) {
       String userName = applicationContext.getEnvironment().getProperty("user.name");
       String userAge = applicationContext.getEnvironment().getProperty("user.age");
       System.err.println("user name : " + userName + "; age: " + userAge);
       TimeUnit.SECONDS.sleep(1);
   }
}
```

![image-20211007162105582](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007162105582.png)

```java
//nacos client 每 10ms 去配置中心进行判断，所以不够一秒钟就输出根据MD5进行判断 因为命名空间public的问题，可以注释掉或者改为dev
//或者是客户端跟服务器版本不一致
```

![image-20211007162122700](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007162122700.png)



```
while (true) {
    String userName = applicationContext.getEnvironment().getProperty("user.name");
    String userAge = applicationContext.getEnvironment().getProperty("user.age");
    System.err.println("user name : " + userName + "; age: " + userAge);
    System.out.println("当前时间：" +new Date());
    TimeUnit.SECONDS.sleep(1);
}
```



![image-20211007162339396](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007162339396.png)



![image-20211007162442188](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007162442188.png)

![image-20211007162449132](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007162449132.png)



控制太发生了变化

![image-20211007162532588](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007162532588.png)

监听信息

![image-20211007162717801](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007162717801.png)

### Nacos-config其他扩展配置

扩展名

![image-20211007200914380](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007200914380.png)

发布以后程序就读取不到配置信息

一旦修改了控制中心中的1properties格式需要做如下修改

![image-20211007201041057](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007201041057.png)

动态感知

![image-20211007201334318](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007201334318.png)

支持profile粒度的配置

根据环境配置相应的配置文件（传统的配置文件的方式）

![image-20211007201752466](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007201752466.png)

```yml
spring:
  profiles:
    active: dev
 #在配置中心通过profile进行配置，
 #只有默认的配置文件才能结合，跟服务名相同的dataid的配置文件
 #对应的dataid:${spring.application.name}
```

![image-20211007203121371](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007203121371.png)

必须加上配置文件中指定的后缀

![image-20211007203103154](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007203103154.png)

![image-20211007203454723](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007203454723.png)

```yml
server:
  port: 8050
spring:
  application:
    # 会自动根据服务名拉取对应的dataia对应的配置文件信息
    name: com.tuling.config.nacos
  profiles:
    active: dev
 #在配置中心通过profile进行配置，
 #只有默认的配置文件才能结合，跟服务名相同的dataid的配置文件
 #对应的dataid:${spring.application.name}
 #profile的后缀必须跟随默认配置文件的格式来
 #关于配置中心的配置才写在bootstrap中
```

dev虽然已经激活，但是尚未发布，所以获取到的信息依然是默认的配置文件的信息

![image-20211007203532688](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007203532688.png)

发布成功后

![image-20211007203719680](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007203719680.png)

```yml
#配置文件的优先级 profile>默认配置文件，优先级大的会覆盖优先级小的，并且会互补
```



namespace的配置

配置为dev

```yml
spring:
  cloud:
    nacos:
      server-addr: 192.168.1.129:8847
      username: nacos
      password: nacos
      config:
        file-extension: yaml
        refresh-enabled: false #nacos 将无法感知配置的变化
        namespace: dev
      #namespace: public
      #nacosclient 默认是properties的后缀
#配置文件的优先级 profile>默认配置文件，优先级大的会覆盖优先级小的，并且会互补
```

控制中心并没有文件，所以会读取不到信息

![image-20211007223438542](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007223438542.png)

![image-20211007223445325](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007223445325.png)

克隆配置文件

![image-20211007223606258](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007223606258.png)

基于组

```yml
spring:
  cloud:
    nacos:
      server-addr: 192.168.1.129:8847
      username: nacos
      password: nacos
      config:
        file-extension: yaml
        #refresh-enabled: false #nacos 将无法感知配置的变化
        namespace: dev
        group: tulingxueyuan
```



自定义扩展的data id配置

两种方式

1.

![image-20211007225457877](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007225457877.png)

![image-20211007225522144](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007225522144.png)

2.

![image-20211007225716895](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007225716895.png)



@RefreshScope

@value注解可以获取到配置中心的值，但是无法动态感知修改后的值，需要利用@RefreshScope注解

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

新建一个控制器

```java
package com.tuling.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author
 * @date 2021年10月07日23:02
 */
@RestController
@RequestMapping("/config")
public class configcontroller {
    @Value("${user.name}")
    public String username;
    @RequestMapping("/show")
    public String show(){
        return username;
    }
}
```



![image-20211007230603157](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007230603157.png)

![image-20211007230609307](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211007230609307.png)

修改配置文件重新发布，不错的话控制台会感知得到，但是页面并不会感知到，需要改注解

—————————————————————————————————

##### 微服务组件sentinel

sentinel分布式系统介绍

sentinel服务雪崩

容错机制：超时机制；服务限流；隔离；服务熔断

分布式系统的流量防卫兵

##### 流控规则

代码演示--新建一个模块，先不去继承父工程，分布式项目可以完成演示

![image-20211008135943846](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211008135943846.png)



```po。xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<!--sentinel核心库-->
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-core</artifactId>
    <version>1.8.0</version>
</dependency>
        <!--如果要使用@SentinelResource-->
        <dependency>
        <groupId>com.alibaba.csp</groupId>
        <artifactId>sentinel-annotation-aspectj</artifactId>
        <version>1.8.0</version>
        </dependency>
```



```java
package com.tuling.sentinelnew.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author
 * @date 2021年10月08日14:08
 */
@Slf4j
@RestController
public class contro {
    private static final String RESOURCE_NAME="hello";
    private static final String USER_RESOURCE_NAME="user";
    private static final String DEGRAD_RESOURCE_NAME="degrad";

    //资源名称一般跟地址名称保持一致
    @RequestMapping(value = "/hello")
    public String hello()   {
        Entry entry=null;
        // 务必保证 finally 会被执行
        try {
            //针对资源进行限制
            entry= SphU.entry(RESOURCE_NAME);
            //被保护的业务逻辑
            String str="hello world ";
            log.info("====="+str+"=====");
            return str;
        } catch (BlockException e) {
            e.printStackTrace();
            //资源访问阻止，被限流或者北降级
            //进行相应的处理操作
            log.info("block!");
            return "被流控了";
        }catch (Exception ex) {
            //若需要配置降级规则，需要通过这种方式李璐业务程序
            Tracer.traceEntry(ex, entry);
        }finally {
            // 务必保证 exit，务必保证每个 entry 与 exit 配对
            if (entry != null) {
                entry.exit();
            }
        }

        return null;
    }


    //流控规则
    @PostConstruct
    private void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<>();
        //为那个资源进行流控
        FlowRule rule = new FlowRule(RESOURCE_NAME);
        // set limit qps to 20
        rule.setCount(1);//阈值为1,1s之内智能访问一次
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);

        rules.add(rule);

        //加载进去
        FlowRuleManager.loadRules(rules);
    }

}
```

请求快一点就被流控了

![image-20211008143953991](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211008143953991.png)

![image-20211008144139136](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211008144139136.png)



@SentinelResource 改善接口中资源定义和被流控降级后的处理方法

![image-20211008145026660](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211008145026660.png)

```java
package com.tuling.sentinelnew.pojo;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author
 * @date 2021年10月08日14:09
 */

public class User {
    private final String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

```

在启动类中配置bean

```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
        //注解支持的配置bean
    @Bean
    public SentinelResourceAspect sentinelResourceAspect(){
        return new SentinelResourceAspect();
    }
}
```

controller.java

```java
/***
*@Author
*@date
   @SentinelResource 改善接口中资源定义和被流控降级后的处理方法
   配置bean-SentinelResourceAspect
 通过value属性去定义资源
 blockHandler 设置流控降级后的处理方法

   --默认该方法需要声明在接口同一类中
*/
@RequestMapping("user")
@SentinelResource(value = USER_RESOURCE_NAME,blockHandler = "blockHandlerForGetUser")
public User getUser(String id){
    return new User("xixi");
}
```

```java

    /****
    *流控处理的方法
    *
    *
    *一定要public
     * 返回值要跟原方法保持一致---->blockHandler设置的方法一致--上面一个方法
     * 参数也要包含原方法参数，顺序要一致，
     * 可以在参数最后添加这个BlockException异常类，可以区分是什么规则的异常
     *
     * 如果不想写在同一个类里面，原方法中的注解去指定在哪里
     * @RequestMapping("user")
     *     @SentinelResource(value = USER_RESOURCE_NAME,blockHandlerClass = User.class,blockHandler = "blockHandlerForGetUser")
     *     public User getUser(String id){
     *         return new User("xixi");
     *     }
     *
     *     且本方法需要声明为static 方法
     *
     *
    */
    public User blockHandlerForGetUser(String id,BlockException ex){
        ex.fillInStackTrace();
        return new User("流控！");

    }
```

添加一个流控规则

```java

//流控规则
@PostConstruct
private void initFlowQpsRule() {
    List<FlowRule> rules = new ArrayList<>();
    //为那个资源进行流控
    FlowRule rule = new FlowRule(RESOURCE_NAME);
    // set limit qps to 20
    rule.setCount(1);//阈值为1,1s之内智能访问一次
    rule.setGrade(RuleConstant.FLOW_GRADE_QPS);

    rules.add(rule);


 
        //为那个资源进行流控
        FlowRule rule2 = new FlowRule();
        rule2.setRefResource(USER_RESOURCE_NAME);
        // set limit qps to 20
        rule2.setCount(1);//阈值为1,1s之内智能访问一次
        rule2.setGrade(RuleConstant.FLOW_GRADE_QPS);

        rules.add(rule2);
        //加载进去
        FlowRuleManager.loadRules(rules);
}
```

天呐呐，一直都不进入限流



降级规则

```java

    /*降级规则*/
    @PostConstruct
    private void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(DEGRAD_RESOURCE_NAME);
        // set threshold RT, 10 ms
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        rule.setCount(2);
        rule.setMinRequestAmount(2);

        rule.setStatIntervalMs(60*1000);
        //熔断市场10s 一旦促发熔断，10s之内会调用处理方法
        //10s后后处于半开状态 恢复接口的请求，第一次请求就失败会再次熔断，
        rule.setTimeWindow(10);
        rules.add(rule);

        DegradeRuleManager.loadRules(rules);
    }

    @RequestMapping("/degrade")
    @SentinelResource(value = DEGRAD_RESOURCE_NAME,entryType = EntryType.IN,
    blockHandler = "blockHandlerForFb"
    )
    public User degrade(String id){
        throw  new RuntimeException("异常");
    }
    public User blockHandlerForFb(String id,BlockException ex){
        return new User("lllll");
    }
```

官网解释

https://github.com/alibaba/Sentinel/wiki/%E5%A6%82%E4%BD%95%E4%BD%BF%E7%94%A8#%E6%B5%81%E9%87%8F%E6%8E%A7%E5%88%B6%E8%A7%84%E5%88%99-flowrule

![image-20211008165337679](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211008165337679.png)

![image-20211008165350502](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211008165350502.png)

第一次第二次都是异常，第四次熔断

![image-20211008165602262](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211008165602262.png)

等个10s再次异常，后恢复熔断



#### 控制台方式

下载对应版本jar,

java -jar xxxx.jar



![image-20211008192329056](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211008192329056.png)

指定端口用户名以及密码启动

java -Dserver.port=8858 -Dsentinel.dashboard.auth.username=xin -Dsentinel.dashboard.auth.password=123456 -jar xxxx.jar

整合

```xml
<!--整合控制台-->
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-transport-simple-http</artifactId>
     <version>1.8.0</version>
</dependency>
```

启动时jiarujvm参数

-Dcsp.sentinel.dashboard.server=ip:port



整合sentinal

order->order-sentinel子项目中进行操作

干掉多余的代码

```yml
package com.tuilngxueyuan;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author
 * @date 2021年10月04日13:40
 */
@RestController
@RequestMapping("/order")
public class ordercontroller {

    @RequestMapping("/add")
    public String add(){
        System.out.println("调用成功");

        return "helloworld";
    }
}
```

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);

    }

}
```

引入依赖

```xml
<!--sentinel启动器-->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
</dependency>
```

```yml
server:
  port: 8861
spring:
  application:
    name: order-sentinel
  cloud:
    sentinel:
      transport:
        dashboard: 127.0.0.1:8858
```

![image-20211008200034411](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211008200034411.png)



规则详解

![image-20211008200146073](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211008200146073.png)

被流控啦

![image-20211008204451077](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211008204451077.png)

```yml

@RequestMapping("/flow")
@SentinelResource(value = "flow",blockHandler = "flowblockHandler")
public String flow(){
    return "正常";
}

 public String flowblockHandler(BlockException e){
    return "流控!!";
}
```

流控并未生效-----重启后信息会消失，因为并未持久化

![](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211008204747779.png)

重新添加流控

![image-20211008205046419](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211008205046419.png)

#### 针对并发线程数进行流控

相当于线程隔离

```java
public String flowblockHandler(BlockException e){
    return "流控!!";
}

@RequestMapping("/flowthre")
@SentinelResource(value = "flow",blockHandler = "flowblockHandler")
public String flowthre() throws InterruptedException {
    TimeUnit.SECONDS.sleep(1);
    return "正常";
}
```

![image-20211008210229492](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211008210229492.png)

利用两个浏览器去测试

![image-20211008210352353](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211008210352353.png)

##### blockexception异常处理

```java
package com.tuilngxueyuan;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;

import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuilngxueyuan.domain.result;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.logging.Logger;


/**
 * @Author
 * @date 2021年10月08日21:07
 */
@Component

public class MyblockExceptionhandler implements BlockExceptionHandler {
   Logger log= (Logger) LoggerFactory.getLogger(this.getClass());
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
    //e.getRule() 包含资源，规则的详细信息
        log.info("BlockException========="+e.getRule());
        result r=null;//该接口有不同的子类，判断就完了
    if(e instanceof FlowException){
        r=result.error(100,"接口限流了");
    }else if(e instanceof DegradeException){
        r=result.error(101,"服务降级了");
    }else if(e instanceof ParamFlowException){
        r=result.error(102,"热点参数被限流了");
    }else if(e instanceof FlowException){
        r=result.error(103,"触发系统保护规则");
    }else if(e instanceof FlowException){
        r=result.error(104,"授权规则不通过");
    }

    //返回json数据
    httpServletResponse.setStatus(500);
    httpServletResponse.setCharacterEncoding("utf-8");
    httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(httpServletResponse.getWriter(),r);
    }
}
```

```java
package com.tuilngxueyuan.domain;

/**
 * @Author
 * @date 2021年10月09日11:55
 */
public class result<T> {
    private Integer code;
    private String msg;
    private T data;//作为统一返回对象。会有不同的数据返回，所以统一声明为泛型

    //通过error快速构建一个result对象出来
    public static result error(Integer code, String msg){
        return new result(code,msg);
    }


    public result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public result(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
```

注视改注解

![image-20211009120517273](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009120517273.png)

修改规则

![image-20211009120904323](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009120904323.png)

反悔了统一的异常处理结果

![image-20211009120913290](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009120913290.png)

流控模式-------->控制台

control.java

```java
@RequestMapping("/add2")
public String add2(){
    System.out.println("下单成功");
    return "生成订单";
}

@RequestMapping("/get")
public String get(){
    return "查询订单";
}
```

由生成订单触发查询订单的限流

![image-20211009122924948](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009122924948.png)

1s访问三次以上，使用JMeter测试

添加线程组

![image-20211009130502309](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009130502309.png)

2min跑300个线程

![image-20211009130653780](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009130653780.png)

添加http请求

![image-20211009130735075](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009130735075.png)

输入配置

![image-20211009130819222](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009130819222.png)

添加监听器

![image-20211009130904335](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009130904335.png)

开启测试

![image-20211009131001051](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009131001051.png)

保存一下文件

![image-20211009131228014](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009131228014.png)

然后去请求查询

![image-20211009131846633](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009131846633.png)



##### 链路流控模式

创建接口并去实现它

```java
@Service
public class orderServiceImpl implements IOrderService {
    @Override
    @SentinelResource(value = "getUser")
    public String getUser() {
        return "查询用户!";
    }
}
```

```java
@Autowired
IOrderService iOrderService;
@RequestMapping("/test1")
public String test1(){
    return  iOrderService.getUser();
}
@RequestMapping("/test2")
public String test2(){
    return  iOrderService.getUser();
}
```

流控模式设置

![image-20211009133957108](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009133957108.png)

![image-20211009134011102](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009134011102.png)

测试发现并没有被流控

，默认没有维护调用链路数

需要进行配置

```yml
server:
  port: 8861
spring:
  application:
    name: order-sentinel
  cloud:
    sentinel:
      transport:
        dashboard: 127.0.0.1:8858
      web-context-unify: false #默认将调用链路收敛 此处将链路展开
```

生成了链路树

![image-20211009134621389](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009134621389.png)

针对test1的getuser进行设置

![image-20211009134710435](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009134710435.png)

test2被流控出500

![image-20211009134824302](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009134824302.png)

@SentinelResource注解出的问题，有这个注解就不会返回result，需要自定义blockHandler=""

```java
@Service
public class orderServiceImpl implements IOrderService {
    @Override
    @SentinelResource(value = "getUser",blockHandler = "blockHandlergetUser")
    public String getUser() {
        return "查询用户!";
    }

    public String blockHandlergetUser(BlockException e) {
        return "流控户!";
    }
}
```

重新配置规则

![image-20211009140217431](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009140217431.png)

##### 流控规则介绍

快速失败：超过阈值的直接拒绝

warm up：预热时长--流量慢慢慢慢的递增的进来直到达到阈值，（洪峰流量）激增流量突然涌入的场景（不让他直接打入数据库导致缓存击穿）（慢慢慢慢的接收流量）

请求波段--（快速失败出的图）

![image-20211009143652704](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009143652704.png)

将失败的qps放在空闲的时段进行请求

![image-20211009143920507](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009143920507.png)

排队等待：（匀速排队-针对脉冲流量）你先等一会，我等下处理完上一批再处理你--处理完一个进去一个，最多能等多少（超时时间--针对空闲时段进行设置）

![image-20211009144213632](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009144213632.png)

还可以在设置打一点的线程--以为空闲时段还挺长的

空闲时段基本上没有啦

![image-20211009145216710](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009145216710.png)

##### 熔断降级规则

在消费者端配置

模拟慢调用

```java
@RequestMapping("/flowthre2")
public String flowthre2() throws InterruptedException {
    TimeUnit.SECONDS.sleep(2);
    System.out.println("正常访问！");
    return "正常";
}
```

![image-20211009202303137](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009202303137.png)

使用压测工具测试

 后会出现半开状态，知道第一次请求不是慢调用才恢复正常的请求

异常比例

声明一个接口去抛出异常

![image-20211009202620820](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009202620820.png)

![image-20211009202713619](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009202713619.png)

![image-20211009202729172](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009202729172.png)

从第六次这样子去熔断降级

![image-20211009202816659](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009202816659.png)

过完熔断期会半开状态，，然后第一次访问就异常会进入熔断

降级主要应用在消费端，流控主要应用在服务提供端，都有一个熔断时长，半开状态

##### Sentinel整合openfeign

![image-20211009203346372](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009203346372.png)

cv  order-openfeigin为order-openfeigin-sentinel

调用store-nacos

```java
package com.tuilngxueyuan.feign;

import com.tuilngxueyuan.config.feignconfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author
 * @date 2021年10月06日13:49
 */
/**
*@Author
*@dat
*2.添加feign接口和方法
*name 指定要调用rest接口所对应的服务名称
*path 指定调用rest接口所在的storecontroller所指定的RequestMapping
 * 如果没有RequestMapping就不写
*/
//@FeignClient(name = "store-service",path = "/stock",configuration = feignconfig.class)
@FeignClient(name = "store-nacos",path = "/stock")
public interface storeFeignService {
    //声明需要调用的rest接口对应的方法--直接复制他
    @RequestMapping("/reduct")
    public String reduct2();
    //声明feign接口就行啦，不需要写实现类

}

```

```java
package com.tuilngxueyuan;

import com.tuilngxueyuan.feign.storeFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author
 * @date 2021年10月04日13:40
 */
@RestController
@RequestMapping("/order")
public class ordercontroller {

    @Autowired
    storeFeignService storeFeignService;

    @RequestMapping("/add")
    public String add(){
        System.out.println("调用成功");
        String msg=storeFeignService.reduct2();

        return "hello  feign  "+msg;
    }
}
```

```yml
server:
  port: 8041
#应用名称(nacos会将该名称当做服务名称)
spring:
  application:
    name: order-service
  cloud:
    nacos:
	  server-addr:  192.168.1.129:8847
      discovery:
        username: nacos
        password: nacos
        namespace: public #命名空间 管理界面的服务列表
```

![image-20211009204847147](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009204847147.png)

加一个除0异常

```java
@RequestMapping("/reduct2")
public String reduct2(){
    int a=1/0;
    System.out.println("扣减成功");
    return "扣减库存"+port;
}
```

此时

![image-20211009211014920](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009211014920.png)

![image-20211009211046594](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009211046594.png)

此时需要进入以来整合

```pom
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
</dependency>
```

添加一个降级的类

```java
@Component
public class storefeignservicefallback implements storeFeignService {
    @Override
    public String reduct2() {
        return "降级了";
    }
}
```

启动配置

```yml
server:
  port: 8041
#应用名称(nacos会将该名称当做服务名称)
spring:
  application:
    name: order-service
  cloud:
    nacos:
      server-addr:  192.168.1.129:8847
      discovery:
        username: nacos
        password: nacos
        namespace: public #命名空间 管理界面的服务列表
#openfeign整合sentinel

feign:
  sentinel:
    enabled: true
```

接口改

```java
package com.tuilngxueyuan.feign;

import com.tuilngxueyuan.config.feignconfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tuilngxueyuan.feign.impl.storefeignservicefallback;
/**
 * @Author
 * @date 2021年10月06日13:49
 */
/**
*@Author
*@dat
*2.添加feign接口和方法
*name 指定要调用rest接口所对应的服务名称
*path 指定调用rest接口所在的storecontroller所指定的RequestMapping
 * 如果没有RequestMapping就不写
*/
//@FeignClient(name = "store-service",path = "/stock",configuration = feignconfig.class)

@FeignClient(name = "store-nacos",path = "/stock",fallback = storefeignservicefallback.class)
public interface storeFeignService {
    //声明需要调用的rest接口对应的方法--直接复制他
    @RequestMapping("/reduct")
    public String reduct2();
    //声明feign接口就行啦，不需要写实现类

}
```

测试

![image-20211009223040974](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009223040974.png)

##### 热点参数限流

添加接口

```java
@RequestMapping("/get/{id}")
@SentinelResource(value = "getById",blockHandler = "hotblockhandler")
public String getByid(@PathVariable("id")Integer id) throws InterruptedException{
    System.out.println("zhengchang");
    return "zhengchang";
    
}

public String hotblockhandler(@PathVariable("id")Integer id,BlockException e){
    return "yichang";
}
```

```yml
spring:
  application:
    name: order-sentinel
  cloud:
    sentinel:
      transport:
        dashboard: 127.0.0.1:8858
      web-context-unify: false #默认将调用链路收敛 此处将链路展开
```

提供者项目

```yml
server:
  port: 8021
#应用名称(nacos会将该名称当做服务名称)
spring:
  application:
    name: store-service
  cloud:
    sentinel:
      transport:
        dashboard: 127.0.0.1:8858
    nacos:
      discovery:
        username: nacos
        password: nacos
        namespace: public #命名空间 管理界面的服务列表
```

单机阈值：1.假设参数大部分值都是热点参数，那单机阈值就主要针对热点参数进行流控， 后续额外针对普通的参数进行流控  2.大部分值都是普通流量，大部分都是热点流量，

![image-20211009233308626](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009233308626.png)

新增完再点编辑出现高级选项

![image-20211009233400483](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009233400483.png)

![image-20211009233541321](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009233541321.png)

1s三次

![image-20211009233711544](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009233711544.png)

针对普通流量进行热点流控

![image-20211009233844186](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009233844186.png)

di11个

![image-20211009233907156](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211009233907156.png)

##### 系统规则

CPU 利用率

![image-20211010003713971](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211010003713971.png)

当cpu利用率超过当前阈值会触发系统保护规则

………………………………

##### 持久化规则

(order-sentinel)

推模式

```xml
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-datasource-nacos</artifactId>
</dependency>
```

在控制中心添加上配置文件

DATAID:order-sentinel-flow-rule

需要提前了解这些属性

```JSON
[
    {
        "resource":"/order/flow",
        "controlBehavior":0,
        "count":2,
        "grade":1,
        "limitApp":"default",
        "strategy":0
    }
]
```

![image-20211011093823363](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211011093823363.png)

然后去配置yml

```yml
spring:
  application:
    name: order-sentinel
  cloud:
    sentinel:
      transport:
        dashboard: 127.0.0.1:8858
      web-context-unify: false #默认将调用链路收敛 此处将链路展开
      datasource:
        flow-rule: #此处可以自定义
          server-addr: 192.168.1.129:8847
          username: nacos
          password: nacos
          dataId: order-sentinel-flow-rule #控制台中的dataid
          rule-type: flow
```

![image-20211011094321667](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211011094321667.png)

控制台的数据改回json格式

![image-20211011095432692](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211011095432692.png)





### seate分布式事务

原子性：要么都成功要么都失败，

一致性：从一个一致性到另外一个一致性的状态，

隔离性：yi个事务的执行不能够被另外一个事务所干扰。

持久性：yi个事务一旦提交就是数据的变化就是永久的

二阶段事务提交



三个角色

协调者：事务管理器，资源管理器



环境搭建

官网地址

https://seata.io/zh-cn/docs/ops/deploy-guide-beginner.html

https://github.com/seata/seata/tree/1.3.0

linux的

db方式配置

修改conf/file.conf中的file为db

![image-20211011222425073](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211011222425073.png)

修改数据库信息

![image-20211011222649028](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211011222649028.png)

创建对应的数据库

db.sql

```sql
-- -------------------------------- The script used when storeMode is 'db' --------------------------------
-- the table to store GlobalSession data
CREATE TABLE IF NOT EXISTS `global_table`
(
    `xid`                       VARCHAR(128) NOT NULL,
    `transaction_id`            BIGINT,
    `status`                    TINYINT      NOT NULL,
    `application_id`            VARCHAR(32),
    `transaction_service_group` VARCHAR(32),
    `transaction_name`          VARCHAR(128),
    `timeout`                   INT,
    `begin_time`                BIGINT,
    `application_data`          VARCHAR(2000),
    `gmt_create`                DATETIME,
    `gmt_modified`              DATETIME,
    PRIMARY KEY (`xid`),
    KEY `idx_gmt_modified_status` (`gmt_modified`, `status`),
    KEY `idx_transaction_id` (`transaction_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- the table to store BranchSession data
CREATE TABLE IF NOT EXISTS `branch_table`
(
    `branch_id`         BIGINT       NOT NULL,
    `xid`               VARCHAR(128) NOT NULL,
    `transaction_id`    BIGINT,
    `resource_group_id` VARCHAR(32),
    `resource_id`       VARCHAR(256),
    `branch_type`       VARCHAR(8),
    `status`            TINYINT,
    `client_id`         VARCHAR(64),
    `application_data`  VARCHAR(2000),
    `gmt_create`        DATETIME(6),
    `gmt_modified`      DATETIME(6),
    PRIMARY KEY (`branch_id`),
    KEY `idx_xid` (`xid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- the table to store lock data
CREATE TABLE IF NOT EXISTS `lock_table`
(
    `row_key`        VARCHAR(128) NOT NULL,
    `xid`            VARCHAR(96),
    `transaction_id` BIGINT,
    `branch_id`      BIGINT       NOT NULL,
    `resource_id`    VARCHAR(256),
    `table_name`     VARCHAR(32),
    `pk`             VARCHAR(36),
    `gmt_create`     DATETIME,
    `gmt_modified`   DATETIME,
    PRIMARY KEY (`row_key`),
    KEY `idx_branch_id` (`branch_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
```

执行脚本生成三张表

![image-20211011224131632](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211011224131632.png)



##### 配置集群模式--注册中心与配置中心

db+nacos部署高可用集群部署方式

修改resg注册文件，修改为nacos

![image-20211011224840342](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211011224840342.png)

除了nacos以外的服务配置可以去掉了

![image-20211011225059998](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211011225059998.png)

修改nacos地址等配置，此处并未使用集群

![image-20211011225905296](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211011225905296.png)

修改配置中心：conf

将file修改为nacos

![image-20211012185526307](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211012185526307.png)



![image-20211012190402736](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211012190402736.png)

将model改为db

![image-20211012185821177](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211012185821177.png)

删除file的配置

修改数据库信息

![image-20211012190226024](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211012190226024.png)

事务分组：解决机房异地，停电，容错

![image-20211012190756617](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211012190756617.png)

![image-20211012190935797](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211012190935797.png)

将配置注册到nacos中：

启动sh/py文件，win可以执行sh(需要git)

本地可以直击启动

远程方式：

![image-20211012202622124](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211012202622124.png)

![image-20211012203533749](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211012203533749.png)

控制台中会有每一项conf中的信息

![image-20211012203508602](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211012203508602.png)

![image-20211012203711455](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211012203711455.png)

集群方式

![image-20211012212833050](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211012212833050.png)

![image-20211012213217919](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211012213217919.png)

其他不变就可以启动集群了



#### 客户端搭建P68



### GATEWAY

功能特性：

基于spring框架和springboot2.0进行构建

动态路由：能够匹配任何请求属性

支持路径重写

集成negspringcloud服务发现功能（nacos,

继承流控降级功能（sentinal

可以对路由指定易于编写的断言和过滤器

#### 核心概念

路由：路由是网关中最基础的一个部分，路由信息包括一个id一个目的url，一组断言工厂，一组过滤器组成，如果断言为真，则说明请求的url和配置的路由匹配

断言：java8中的断言函数，springcloudgateway中的断言函数类型是spring5.0框架中的serverwebexchange，断言函数允许开发者去定义匹配的http request中的任何信息。比如说请求头和参数等

过滤器：springcloudgateway中的过滤器分为gatewayfilter 和global filter ，filter可以对请求和相应进行处理

#### 代码演示

先创建一个子项目

添加依赖

```xml
<dependency>
    <!--springcloud的东西，所以父项目需要提前做好版本管理-->
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

```yml
server:
  port: 8088
spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      routes:
        - id: order_route    #路由的唯一标志,路由到order
          uri: http://localhost:8020 #需要转发到的订单
          #断言规则  用于路由规则的匹配
          predicates:
            - Path=/order-serv/**
             #  http://localhost:8088/order-serv/order/add转发到
             #  http://localhost:8020/order-serv/order/add
          filters:
            - -StripPrefix=1  #内置的一种过滤器，转发前去掉一层路径
            # http://localhost:8020/order/add
        #- id: store_route
```

启动

![image-20211013205614835](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013205614835.png)

访问 http://localhost:8088/order-serv/order/add

转发到了订单

![image-20211013203140854](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013203140854.png)

gateway整合nacos

添加nacos的依赖

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
```

复制一个配置文件

![image-20211013204102509](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013204102509.png)

做简单的修改

修改以及添加的部分

```yml
server:
  port: 8088
spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      routes:
        - id: order_route    #路由的唯一标志,路由到order
          uri: http://localhost:8020 #需要转发到的订单
          #断言规则  用于路由规则的匹配
          predicates:
            - Path=/order-serv/**
             #  http://localhost:8088/order-serv/order/add转发到
             #  http://localhost:8020/order-serv/order/add
          filters:
            - StripPrefix=1  #内置的一种过滤器，转发前去掉一层路径
            # http://localhost:8020/order/add
        #- id: store_route
```

![image-20211013204459631](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013204459631.png)

能访问及整个成功

![image-20211013204551820](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013204551820.png)

简写的方式

```yml
server:
  port: 8088
spring:
  application:
    name: api-gateway
  cloud:
    nacos:
      discovery:
        server-addr: 192.168.1.129:8847
        username: nacos
        password: nacos

    gateway:
      discovery:
        locator:
          enabled: true #是否启动自动识别nacos服务
```

访问服务的前缀名称修改

![image-20211013205033516](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013205033516.png)

#### 断言

作用：当我们请求gateway的时候回对请求进行匹配，成功就转发

断言工厂

![image-20211013205450645](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013205450645.png)

After（befor between 类似）

```yml
server:
  port: 8088
spring:
  application:
    name: api-gateway
  cloud:
    nacos:
      discovery:
        server-addr: 192.168.1.129:8847
        username: nacos
        password: nacos

    gateway:
      routes:
        - id: order_route    #路由的唯一标志,路由到order
          uri: lb://order-service #需要转发到的订单 lb:使用nacos中的本地负载均衡策略 order-service 服务名
          #断言规则  用于路由规则的匹配
          predicates:
            - Path=/order/**
             #  http://localhost:8088/order-serv/order/add转发到
             #  http://localhost:8020/order-serv/order/add
            - After=2019-12-31T23:59:59.789+08:00[Asia/Shanghai]
         # filters:
          #  - StripPrefix=1  #内置的一种过滤器，转发前去掉一层路径
            # http://localhost:8020/order/add
        #- id: store_route
```

去掉前缀

![image-20211013210530139](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013210530139.png)

当前时间晚于设置的时间可以匹配

![image-20211013210657830](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013210657830.png)

请求头

```yml
server:
  port: 8088
spring:
  application:
    name: api-gateway
  cloud:
    nacos:
      discovery:
        server-addr: 192.168.1.129:8847
        username: nacos
        password: nacos

    gateway:
      routes:
        - id: order_route    #路由的唯一标志,路由到order
          uri: lb://order-service #需要转发到的订单 lb:使用nacos中的本地负载均衡策略 order-service 服务名
          #断言规则  用于路由规则的匹配
          predicates:
            - Path=/order/**
             #  http://localhost:8088/order-serv/order/add转发到
             #  http://localhost:8020/order-serv/order/add
            - After=2020-12-31T23:59:59.789+08:00[Asia/Shanghai]
            - Header = X-Request-Id, \d+  #数字正则表达式
         # filters:
          #  - StripPrefix=1  #内置的一种过滤器，转发前去掉一层路径
            # http://localhost:8020/order/add
        #- id: store_route
```

![image-20211013211430722](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013211430722.png)

请求头非数字

![image-20211013211513605](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013211513605.png)

get

![image-20211013211612763](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013211612763.png)

![image-20211013211654341](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013211654341.png)

post

![image-20211013211713460](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013211713460.png)

Name

![image-20211013211758835](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013211758835.png)

![image-20211013211832052](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013211832052.png)

![image-20211013211913737](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013211913737.png)

![image-20211013212021229](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013212021229.png)

![image-20211013212037114](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013212037114.png)

#### 自定义路由断言工厂

1.必须是一个bean

2.类必须佳航RoutePredicateFactory作为结尾

3.继承抽象的路由断言工厂（AbstractRoutePredicateFactory）

4.必须声明一个静态的内部类，声明属性来接收配置文件中的对应的断言信息

5.需要结合ShortcutOrder进行传递

6.通过apply进行逻辑判断，true匹配成功，false匹配失败



传入断言工厂的值(cheakAuth) 下图写错

![image-20211013220935681](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013220935681.png)

```java
package com.tulingxueyuan.pre;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.cloud.gateway.handler.predicate.QueryRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.prefs.AbstractPreferences;

/**
 * @Author
 * @date 2021年10月13日21:59
 */
@Component
public class CheakAuthRoutePredicateFactory extends AbstractRoutePredicateFactory<CheakAuthRoutePredicateFactory.Config> {


    public CheakAuthRoutePredicateFactory() {
        super(CheakAuthRoutePredicateFactory.Config.class);
    }

    public List<String> shortcutFieldOrder() {
        return Arrays.asList("name");//Config类中定义的值
    }

    public Predicate<ServerWebExchange> apply(CheakAuthRoutePredicateFactory.Config config) {
        return new GatewayPredicate() {
            public boolean test(ServerWebExchange exchange) {
                //进行逻辑的判断
                if(config.getName().equals("nanning")){
                    return true;
                }else {
                    return false;
                }
            }
        };
    }

    //用于接收配置文件中断言信息的值
    @Validated
    public static class Config {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
```

![image-20211013221938041](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013221938041.png)

![image-20211013222201511](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013222201511.png)

修改为，无法访问

![image-20211013222241348](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013222241348.png)

#### 过滤器工厂

添加一个请求头

```yml
server:
  port: 8088
spring:
  application:
    name: api-gateway
  cloud:
    nacos:
      discovery:
        server-addr: 192.168.1.129:8847
        username: nacos
        password: nacos

    gateway:
      routes:
        - id: order_route    #路由的唯一标志,路由到order
          uri: lb://order-service #需要转发到的订单 lb:使用nacos中的本地负载均衡策略 order-service 服务名
          #断言规则  用于路由规则的匹配
          predicates:
            - Path=/order/**
             #  http://localhost:8088/order-serv/order/add转发到
             #  http://localhost:8020/order-serv/order/add
            #- After=2020-12-31T23:59:59.789+08:00[Asia/Shanghai]
            #- Header=X-Request-Id,\d+  #数字正则表达式
            #- Method=GET
            #- Query=name,xx|yy  #参数中需要有xx|yy之一，否则不通过
            #- CheakAuth=nanning
          filters:
            #- StripPrefix=1  #内置的一种过滤器，转发前去掉一层路径
            # http://localhost:8020/order/add
            - AddRequestHeader=X-Request-color,red
        #- id: store_route
```

order-nacos项目中添加接口

```java
    @RequestMapping("/header")
    public String header(@RequestHeader("X-Request-color") String color){
        return "helloworld"+color;
    }
```

![image-20211013223241642](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013223241642.png)

重定向

![image-20211013223846445](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013223846445.png)

#### 自定义过滤器工厂

复制原来的过来改吧改吧成最初的框架（config里面也可以去掉）

重命名一下类名吧(myRedirectToAuthGatewayFilterFactory)

```java
package com.tulingxueyuan.pre;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.RedirectToGatewayFilterFactory;
import org.springframework.cloud.gateway.support.GatewayToStringStyler;
import org.springframework.cloud.gateway.support.HttpStatusHolder;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * @Author
 * @date 2021年10月13日23:33
 */
public class myRedirectToGatewayFilterFactory extends AbstractGatewayFilterFactory<myRedirectToGatewayFilterFactory.Config> {

    public myRedirectToGatewayFilterFactory() {
        super(myRedirectToGatewayFilterFactory.Config.class);
    }

    public List<String> shortcutFieldOrder() {
        return Arrays.asList("");
    }

    public GatewayFilter apply(myRedirectToGatewayFilterFactory.Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                return null;
            }
        }；
    }

  
    public static class Config {
        String status;
        String url;

        public Config() {
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
```

填内容

```java
package com.tulingxueyuan.pre;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @Author
 * @date 2021年10月13日23:33
 */
@Component
public class myRedirectToAuthGatewayFilterFactory extends AbstractGatewayFilterFactory<myRedirectToAuthGatewayFilterFactory.Config> {

    public myRedirectToAuthGatewayFilterFactory() {
        super(myRedirectToAuthGatewayFilterFactory.Config.class);
    }

    public List<String> shortcutFieldOrder() {
        return Arrays.asList("value");
    }

    public GatewayFilter apply(myRedirectToAuthGatewayFilterFactory.Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                String city=exchange.getRequest().getQueryParams().getFirst("city");

                //获取city参数，如果不等于value则失败
                if(StringUtils.isNotBlank(city)){
                    if(config.getValue().equals(city)){
                       return chain.filter(exchange);
                    }else {
                        exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
                       return  exchange.getResponse().setComplete();
                    }
                }
                //正常请求
                return chain.filter(exchange);
            }
        };
    }


    public static class Config {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
```

```yml
server:
  port: 8088
spring:
  application:
    name: api-gateway
  cloud:
    nacos:
      discovery:
        server-addr: 192.168.1.129:8847
        username: nacos
        password: nacos

    gateway:
      routes:
        - id: order_route    #路由的唯一标志,路由到order
          uri: lb://order-service #需要转发到的订单 lb:使用nacos中的本地负载均衡策略 order-service 服务名
          #断言规则  用于路由规则的匹配
          predicates:
            - Path=/order/**
             #  http://localhost:8088/order-serv/order/add转发到
             #  http://localhost:8020/order-serv/order/add
            #- After=2020-12-31T23:59:59.789+08:00[Asia/Shanghai]
            #- Header=X-Request-Id,\d+  #数字正则表达式
            #- Method=GET
            #- Query=name,xx|yy  #参数中需要有xx|yy之一，否则不通过
            #- CheakAuth=nanning
          filters:
            #- StripPrefix=1  #内置的一种过滤器，转发前去掉一层路径
            # http://localhost:8020/order/add
            #- AddRequestHeader=X-Request-color,red
            #- RedirectTo=303,https://www.baidu.com/
            - MyRedirectToAuth=city
        #- id: store_route
```

![image-20211013235825687](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211013235825687.png)

#### 全局过滤器

全局过滤器和局部过滤器的区别

局部：针对某个路由   需要在路由中定义

全局：针对所有路由请求 一旦定义就会投入使用



自定义一个过滤

```java
package com.tulingxueyuan.pre;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author
 * @date 2021年10月14日14:02
 */
@Component
public class LogFilter implements GlobalFilter {
    Logger log=LoggerFactory.getLogger(this.getClass());
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info(exchange.getRequest().getPath().value());

        return chain.filter(exchange);
    }
}
```

惊了

![image-20211014140830753](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211014140830753.png)

![image-20211014141008053](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211014141008053.png)

##### 请求日志处理与跨域

添加跨域配置

```yml
server:
  port: 8088
spring:
  application:
    name: api-gateway
  cloud:
    nacos:
      discovery:
        server-addr: 192.168.1.129:8847
        username: nacos
        password: nacos
    gateway:
      routes:
        - id: order_route    #路由的唯一标志,路由到order
          uri: lb://order-service #需要转发到的订单 lb:使用nacos中的本地负载均衡策略 order-service 服务名
          #断言规则  用于路由规则的匹配
          predicates:
            - Path=/order/**
             #  http://localhost:8088/order-serv/order/add转发到
             #  http://localhost:8020/order-serv/order/add
            #- After=2020-12-31T23:59:59.789+08:00[Asia/Shanghai]
            #- Header=X-Request-Id,\d+  #数字正则表达式
            #- Method=GET
            #- Query=name,xx|yy  #参数中需要有xx|yy之一，否则不通过
            #- CheakAuth=nanning
          filters:
            #- StripPrefix=1  #内置的一种过滤器，转发前去掉一层路径
            # http://localhost:8020/order/add
            #- AddRequestHeader=X-Request-color,red
            #- RedirectTo=303,https://www.baidu.com/
            - MyRedirectToAuth=city
        #- id: store_route
      #跨域配置
      globalcors:
        cors-configurations:
          '[/**]':  #允许访问的跨域资源
            allowedOrigins:  "*" # 跨域允许的来源
            allowedMethods:  #
              -GET
              -POST
```

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>get user</title>
    <script src="http://apps.bdimg.com/libs/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
    <div>
        <table>
            <thead>
            <tr>
                <td>id</td>
                <td>username</td>
                <td>age</td>
            </tr>
            </thead>
            <tbody id="userlist">

            </tbody>
        </table>
    </div>
<input type="button" value="userlist" onclick="getData()">
<script>
    function getData() {
        $.get('httpL//localhost:8088/order/add',function (data) {
            alert(data)

        });
    }
</script>
</body>
</html>
```

避开本域

![image-20211014143307375](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211014143307375.png)

重启后访问成功

配置类的方法

```java
package com.tulingxueyuan.pre;

/**
 * @Author
 * @date 2021年10月14日14:48
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * 解决跨域问题
 */
@Configuration
public class CorsConfig  {
    @Bean
    public CorsWebFilter corsWebFilter(){
        CorsConfiguration conf=new CorsConfiguration();
        conf.addAllowedHeader("*");
        conf.addAllowedOrigin("*");
        conf.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**",conf);
        return new CorsWebFilter(source);
    }
}
```

测试同上



整合sentinel流控降级

添加依赖

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-alibaba-sentinel-gateway</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
</dependency>
```

添加配置

```yml
server:
  port: 8088
spring:
  application:
    name: api-gateway
  cloud:
    nacos:
      discovery:
        server-addr: 192.168.1.129:8847
        username: nacos
        password: nacos
    gateway:
      routes:
        - id: order_route    #路由的唯一标志,路由到order
          uri: lb://order-service #需要转发到的订单 lb:使用nacos中的本地负载均衡策略 order-service 服务名
          #断言规则  用于路由规则的匹配
          predicates:
            - Path=/order/**
             #  http://localhost:8088/order-serv/order/add转发到
             #  http://localhost:8020/order-serv/order/add
            #- After=2020-12-31T23:59:59.789+08:00[Asia/Shanghai]
            #- Header=X-Request-Id,\d+  #数字正则表达式
            #- Method=GET
            #- Query=name,xx|yy  #参数中需要有xx|yy之一，否则不通过
            #- CheakAuth=nanning
          #filters:
            #- StripPrefix=1  #内置的一种过滤器，转发前去掉一层路径
            # http://localhost:8020/order/add
            #- AddRequestHeader=X-Request-color,red
            #- RedirectTo=303,https://www.baidu.com/
            #- MyRedirectToAuth=city
        #- id: store_route
      #跨域配置
#      globalcors:
#        cors-configurations:
#          '[/**]':  #允许访问的跨域资源
#            allowedOrigins:  "*" # 跨域允许的来源
#            allowedMethods:  #
#              - GET
#              - POST

#配置sentinel
    sentinel:
      transport:
        dashboard: 127.0.0.1:8858
```

启动本地的sentinel

![image-20211014172023998](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211014172023998.png)

一顿复制order-sentinel中的接口代码到order-nacos

```java
/***************************************************************************/


    @RequestMapping("/flow")
    //   @SentinelResource(value = "flow",blockHandler = "flowblockHandler")
    public String flow(){
        return "正常";
    }



    @RequestMapping("/flowthre")
    public String flowthre() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        return "正常";
    }

    @RequestMapping("/add2")
    public String add2(){
        System.out.println("下单成功");
        return "生成订单";
    }

    @RequestMapping("/get")
    public String get(){
        return "查询订单";
    }



    @RequestMapping("/flowthre2")
    public String flowthre2() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        System.out.println("正常访问！");
        return "正常";
    }
```

启动其中一个资源注册到sentinel

![image-20211014174522531](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211014174522531.png)

流控他

![image-20211014174752831](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211014174752831.png)

一秒访问3次以上

![image-20211014174829004](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211014174829004.png)

sentinel整合流控降级详细配置

针对本地ip进行流控

![image-20211014201638328](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211014201638328.png)

![image-20211014201938189](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211014201938189.png)

就只针对本地ip流控



针对请求头设置

![image-20211014202222836](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211014202222836.png)

![image-20211014202239533](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211014202239533.png)

根据url参数

![image-20211014202324284](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211014202324284.png)

![image-20211014202341866](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211014202341866.png)

![image-20211014202350866](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211014202350866.png)

设置成别的就不会限流了

![image-20211014202419435](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211014202419435.png)

api

![image-20211014202835503](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211014202835503.png)

![image-20211014202942912](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211014202942912.png)

#### 自定义异常

```java
package com.tulingxueyuan.pre;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * @Author
 * @date 2021年10月14日20:54
 */
@Configuration
public class gatewayconf {
    @PostConstruct
    public void Unit(){
        BlockRequestHandler blockRequestHandler  =new BlockRequestHandler() {
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
                //自定义的匿名类----异常处理
                System.out.println(throwable);
                HashMap<String,String> map=new HashMap<>();
                map.put("code", HttpStatus.TOO_MANY_REQUESTS.toString());
                map.put("msg", "限流了");


                return ServerResponse.status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(map))
                        ;
            }

        };
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }
}
```

![image-20211014210604379](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211014210604379.png)

方式2

![image-20211014210717732](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211014210717732.png)

集群

![image-20211014211029820](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211014211029820.png)





## SkyWalking链路追踪

分布式系统的应用程序性能监视工具，专门为微服务，云原生架构和基于容器的架构而设计，包括分布式追踪，性能指标分析，应用和服务依赖分析等

主要功能特性

- 多种监控手段，可以通过语言探针和servicemesh获得监控的数据

- 支持多种语言自动探针，包括java .net Core 和Node.js

- 轻量高效，无需大数据平台和大量的服务器资源

- 模块化，UI 存储，集群管理都有多种机制可选

- 支持警告

- 优秀的可视化解决方案

  #### linux部署

  

解压文件修改端口

![image-20211015190103122](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211015190103122.png)

启动

![image-20211015190359642](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211015190359642.png)

运行日志

![image-20211015190513335](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211015190513335.png)

ui

![image-20211015190636103](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211015190636103.png)

#### skywalking client搭建

##### windows环境--在IDEA中使用SkyWalking

在gateway中进行操作

![image-20211015191421531](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211015191421531.png)

![image-20211015191448538](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211015191448538.png)

注释掉配置文件中的sentinel

启动nacos服务

启动gateway 对应的order nacos

![image-20211015192024009](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211015192024009.png)

刷新skywalking

会显示服务

![image-20211015192106917](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211015192106917.png)

然后仪表盘拓扑图都没有数据

需要拷贝agent/optional-plugins下的gateway插件到agent/plugins目录

（2.1.x）



![image-20211016210804033](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016210804033.png)

重启一下服务

访问接口，查看控制台

![image-20211016211504943](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016211504943.png)

拓扑图

![image-20211016211744703](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016211744703.png)

追踪--调用链路

![image-20211016211810096](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016211810096.png)

#### linux环境

![image-20211016211944133](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016211944133.png)

![image-20211016212017892](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016212017892.png)

![image-20211016212106658](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016212106658.png)

#### Skywalking接入多个微服务

确定·seata下面的alibab-order-seata的微服务名称

是什么，gateway的gateway-routes-id 一栏也要一致

确定·seata下面的alibab-order-seata的nacos连接的是那个，gateway也改成那里

![image-20211016214557185](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016214557185.png)

![image-20211016214615324](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016214615324.png)

![image-20211016214638163](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016214638163.png)

访问add

![image-20211016220609211](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016220609211.png)

改错地方了

![image-20211016220704643](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016220704643.png)

报500的异常

![image-20211016220753189](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016220753189.png)

![image-20211016220813989](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016220813989.png)

拓扑

![image-20211016220918277](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016220918277.png)

追踪

![image-20211016220939686](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016220939686.png)

### SkyWoalking 数据持久化

![image-20211016221858320](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016221858320.png)

修改为mysql

![image-20211016221727252](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016221727252.png)

![image-20211016221819540](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016221819540.png)

建库

![image-20211016222211420](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016222211420.png)

表不需要我们操作，启动的时候回自动创建

重启服务

OAP会自动关闭

先干掉log下面的文件

再次关闭，然后看日志---->获取不到驱动的实例

![image-20211016222531999](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016222531999.png)

去赋值一个mysql驱动到apache-skywalking-apm-bin-es7\oap-libs

ex……

![image-20211016222823601](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016222823601.png)

重启

自动建表

![image-20211016223041088](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016223041088.png)

启动服务

![image-20211016223222027](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016223222027.png)

![image-20211016223251636](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016223251636.png)

代各项数据都有了，重启服务看看持久化没有

### 自定义链路追踪

如果我们希望对项目中的业务方法实现链路追踪，方便我们排查问题，我们可以这样

引入依赖

![image-20211016225452633](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016225452633.png)

![image-20211016225427027](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016225427027.png)

添加注解

![image-20211016225535455](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016225535455.png)

重启项目

![image-20211016225622380](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016225622380.png)

![image-20211016225659856](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016225659856.png)

![image-20211016225720161](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016225720161.png)

记录返回值

![image-20211016225831715](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016225831715.png)

指定返回值与参数

![image-20211016225906327](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016225906327.png)

![image-20211016225958590](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016225958590.png)

重新请求接口

点击业务方法

![image-20211016230046055](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016230046055.png)

加在业务方法上

![image-20211016230209598](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016230209598.png)

重启一下，这里小失误1

点击业务方法



![image-20211016235255469](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016235255469.png)

#### 性能剖析

在我们请求接口的时候，响应特别的慢，可以通过性能剖析去快速定位比较慢的代码

选择服务，

![image-20211016235942270](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211016235942270.png)

请求5-6次

![image-20211017000029717](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017000029717.png)

分析调用栈

![image-20211017000121909](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017000121909.png)

#### SKwalking集成日志框架



在项目中创建一个xml

![image-20211017104705891](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017104705891.png)

![image-20211017105414132](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017105414132.png)

![image-20211017105401286](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017105401286.png)



改造

![image-20211017105511622](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017105511622.png)

添加依赖



![image-20211017104957310](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017104957310.png)

可以根据tid去在控制台中进行搜索

![image-20211017105211170](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017105211170.png)

将请求的日志发布到skywalking

添加一个追加器

![image-20211017105704969](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017105704969.png)

添加追加器的引用

![image-20211017105805256](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017105805256.png)

![image-20211017105910843](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017105910843.png)

如果skywalking不是本地的，又需要使用日志上报需要

![image-20211017110139316](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017110139316.png)

### Skwalking告警功能

告警规则

1. 过去3分钟内服务平均响应时间超过1s;

2. 过去2分钟服务成功率低于80%

3. 过去3分钟内服务响应时间超过1s的百分比

4. 服务实例在过去2分钟内平均响应时间超过1s，并且实例名称与正则表达式匹配

5. 过去2分钟内断电响应时间超过1s

6. 过去2分钟内数据库访问平均时间超过1s

7. 过去2分钟内端点关系平均响应时间超过1s

   

配置文件

![image-20211017125046927](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017125046927.png)

#### 网络狗钩子

![image-20211017133112769](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017133112769.png)

配置一个webhooks

配置dto

```java
@Getter
@Setter
public class swalarmDTO {

    private int scopeId;
    private String scope;
    private String name;
    private String id0;
    private String id1;
    private String ruleName;
    private String AlarmMessage;
    private List<Tag> tags;
    private long startTime;
    private transient int period;
    private transient boolean onlyAsCondition;

    private List<Tag> events = new ArrayList<>(2);


    @Data
    public static class Tag{
    private String key;
    private String value;
    }
```

配置一个接口去接收一个dto

```java
/**
 * @Author
 * @date 2021年10月17日16:39
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class swAlarmControl {
    @PostMapping("/receive")
    public void receive(@RequestBody List<swalarmDTO> alarmlist){
        SimpleMailMessage msg=new SimpleMailMessage();
        msg.setFrom("1713695034@qq.com");
        msg.setTo("417652977@qq.com");
        msg.setSubject("测试");
        msg.setText("测试2");

        String context = getConnect(alarmlist);
        log.info("邮件已经发送……"+context);

    }
    private String getConnect(List<swalarmDTO> swalarmDTOS){
        StringBuffer sb=new StringBuffer();
        for (swalarmDTO dto:swalarmDTOS){
         sb.append("scopeId:").append(dto.getScopeId())
         .append("\nscop:").append(dto.getScope())
         .append("\n目标scop的实体名称:").append(dto.getName())
         .append("\nscop的实体ID:").append(dto.getId0())
         .append("\nid1:").append(dto.getId1())
         .append("\n告警规则名称:").append(dto.getRuleName())
         .append("\n告警消息内rong:").append(dto.getAlarmMessage())
         .append("\n告警时间:").append(dto.getStartTime())
         .append("\n标签:").append(dto.getTags())
         .append("\n\n____________________:");
        }
        return sb.toString();
    }
}
```

告警信息

![image-20211017170222836](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017170222836.png)

![image-20211017170310144](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017170310144.png)

自定义使用那一个通讯工具

![image-20211017170553111](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017170553111.png)

#### SKYWALKING高可用

![image-20211017170642895](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017170642895.png)

skywalking的配置文件

![image-20211017170931592](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017170931592.png)

![image-20211017170942512](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017170942512.png)

![image-20211017170958384](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017170958384.png)

此处是逗号分割配置多个

![image-20211017171050398](C:\Users\xin\AppData\Roaming\Typora\typora-user-images\image-20211017171050398.png)

