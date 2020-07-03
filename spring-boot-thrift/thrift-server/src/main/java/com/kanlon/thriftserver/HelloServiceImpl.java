package com.kanlon.thriftserver;

import org.apache.thrift.TException;

public class HelloServiceImpl implements Hello.Iface {

    @Override
    public String helloString(String param) throws TException {
        System.out.println("thrift客户端请求的参数为："+param);
        return "hello: " + param;
    }
}
