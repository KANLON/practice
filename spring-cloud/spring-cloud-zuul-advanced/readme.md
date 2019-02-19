# spring zuul 网关服务的高级应用

注意事项：

1.  在重写eureka服务提供的使用，发现应该启动了就关闭了，后来加上以下依赖才成功，应该是自己的项目之前不是web项目导致其不能一直在线，而其他网关，熔断器等服务都是可以一直在线的，默认应该是web应用了，所以就不用加。

```
        <!-- web应用 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```
