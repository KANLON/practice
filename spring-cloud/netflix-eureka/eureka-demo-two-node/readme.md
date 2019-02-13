# spring cloud Netflix eureka练习2（双节点）

注意事项：<br/>

1. 按照[纯洁的微笑的博客](http://www.ityouknow.com/springcloud/2017/05/10/springcloud-eureka.html)搭建，发现停止其中一个节点后，并没有出现节点不可用的图，而是进入了[Eureka的自我保护模式](http://www.itmuch.com/spring-cloud-sum-eureka/),解决方法有[三种](https://www.cnblogs.com/xishuai/p/spring-cloud-eureka-safe.html)，1，关闭自我保护模式.2,降低renewalPercentThreshold的比例.3,部署多个 Eureka Server 并开启其客户端行为，可以通过Instances currently registered with Eureka判断(推荐)

2. resource下的配置文件应该是只有peer1和peer2两个，要删除application.properties默认的这个，否则会[一起加载进来的](https://blog.csdn.net/u010606397/article/details/80713968)。