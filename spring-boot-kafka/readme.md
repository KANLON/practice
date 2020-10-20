# Spring Boot kafka的Demo

该项目主要展示的Spring Boot 如何使用Kakfa作为消息队列，以便在使用的时候能快速集成


这里为了解决集群时的情况，消费者组采用随机数的形式，以让每个机器上的服务都能收到的订阅的消息


主要的内容在：`KafkaMsgService.java` 文件中。

官方文档地址： [https://spring.io/projects/spring-kafka](https://spring.io/projects/spring-kafka)

文档中有版本的对应关系和一些配置信息


注意：

1. 如果要保证接受和发送的数据保持顺序，可以设置kafka topic只设置一个分区或者，生产者发送消息和消费者接受消息都需要用同一个分区。

2. 启动之前需要，更换`application.yml` 中的kafka集群地址才能启动成功，和更改存在的topic信息才能正常发送或接受kafka消息

3. 启动之后可以使用 这个运行这个curl命令 `curl --location --request GET 'localhost:8080/api/kafka/send?testMsg=1243'` 来测试kafka发送消息，日志中会有打印出发送和接受消息记录。
