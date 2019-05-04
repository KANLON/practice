# spring-boot-grpc 例子

这个练习主要是练习grpc与spring boot整合的情况，主要使用了net.devh的grpc-server-spring-boot-starter来整合。

项目结构说明：

1.  `spring-boot-grpc-eureka` 是spring cloud的注册中心，grpc的提供者和消费者都要注册到这里，以便可以通过应用名调用远程服务。
2.  `spring-boot-grpc-consumer` grpc的消费方，负责调用远程服务的方法，主要的类是：`TestController`和`GrpcConsumerService`
3. `spring-boot-grpc-provider` 是grpc的提供方，负责提供方法，让消费方可以远程调用，主要的类为：`GrpcServerService`


## 参考的其他项目：

[/other-project](/other-project)
