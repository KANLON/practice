# 这个项目主要用来练习 thrift 接口启动和调用

0. 定义好thrift接口格式文件，例如：resource 下的 hello.thrift
1. 执行 `thrift-0.13.0.exe -r -gen java hello.thrift`，将生成的Hello.java 放到对应服务端和客户端中的包中
2. 启动 thrift-server 中的spring boot 启动类，可以看得到：`服务端开启....`
3. 运行 thrift-client 中的 com.kanlon.thriftclient.ThriftClient 主方法，可以看到发送了thrift 请求，并且服务端接受到了
客户端：
```
客户端启动....
hello: hans
```

服务端：
```
服务端开启....
thrift客户端请求的参数为：hans
```

参考：[Spring Boot 中使用 thrift 入门](https://segmentfault.com/a/1190000015001012)
