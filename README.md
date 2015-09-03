# kratos
基于Java语言编写的轻量级分库分表(Sharding)中间件，丰富的Sharding算法支持(2类4种分片算法)，能够方便DBA实现库的极速扩容和降低数据迁移成本。Kratos站在巨人的肩膀上(SpringJdbc)，采用与应用集成架构，放弃通用性，只为换取更好的执行性能与降低分布式环境下外围系统的down机风险。

## kratos的优点：
1、动态数据源的无缝切换；

2、master/slave一主一从读写分离；

3、单线程读重试(取决于的数据库连接池是否支持)；

4、单独支持Mysql数据库；

5、非Proxy架构，与应用集成，应用直连数据库，降低外围系统依赖带来的down机风险；

6、使用简单，侵入型低，站在巨人的肩膀上，依赖于Spring JDBC；

7、分库分表路由算法支持2类4种分片模式，库内分片/一库一片；

8、提供自动生成sequenceId的API支持；

9、提供自动生成配置文件的支持，降低配置出错率；

Kratos的使用手册：http://gao-xianglong.iteye.com/blog/2237277

Kratos的分片模型：http://gao-xianglong.iteye.com/blog/2238901

Kratos的注意事项：http://gao-xianglong.iteye.com/blog/2239007
