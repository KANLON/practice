# 服务消费调用

注意事项：

1. 在纯洁的微信的博客中的[springcloud(三)：服务提供与调用](http://www.ityouknow.com/springcloud/2017/05/12/eureka-provider-constomer.html),说服务调用和1、pom包配置 和服务提供者一致，但是实际是比服务提供者多了`spring-cloud-starter-feign`依赖,需要留意。

2. 再添加熔断器后的回退类后，使用自动注入@Autowired注入ConsumerController会不太精确， 由于 FeignClient隐性注入，所以使用@Resource通过byName注入更能精确查找。参考：https://www.cnblogs.com/think-in-java/p/5474740.html