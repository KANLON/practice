package com.linshen.grpcspringcloudconsumer;

import com.linshen.grpc.cloud.lib.GreeterGrpc;
import com.linshen.grpc.cloud.lib.GreeterOuterClass;
import io.grpc.Channel;
import net.devh.springboot.autoconfigure.grpc.client.GrpcChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrpcClientService {

    private Logger logger = LoggerFactory.getLogger(GrpcClientService.class);

    @Autowired
    private GrpcChannelFactory grpcChannelFactory;

    //    @GrpcClient("grpc-spring-cloud-provider")
    //    private Channel serverChannel;

    public String sendMessage(String param1, String param2) {
        Channel channel = grpcChannelFactory.createChannel("grpc-spring-cloud-provider");
        logger.info("实体化的频道为：" + channel.toString());
        logger.info(channel.hashCode() + "");
        GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);
        //GreeterGrpc.GreeterBlockingStub stub= GreeterGrpc.newBlockingStub(serverChannel);
        GreeterOuterClass.HelloReply response =
                stub.sayHello(GreeterOuterClass.HelloRequest.newBuilder()
                        .setParam1(param1)
                        .setParam2(param2)
                        .build());
        return response.getDataList().toString();
    }
}
