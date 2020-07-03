package com.kanlon.thriftclient;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * thrift的客户端程序
 *
 * @author zhangcanlong
 * @since 2020/7/3 21:50
 **/
public class ThriftClient {


    public static void main(String[] args) {
             startUpClient();
    }

    /**
     * 启动客户端
     */
    public static  void startUpClient(){
        System.out.println("客户端启动....");
        TTransport transport = null;
        try {
            transport = new TSocket("localhost", 9898, 30000);
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            Hello.Client client = new Hello.Client(protocol);
            transport.open();
            String result = client.helloString("hans");
            System.out.println(result);
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }


}
