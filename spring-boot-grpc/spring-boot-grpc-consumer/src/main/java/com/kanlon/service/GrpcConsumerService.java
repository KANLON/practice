package com.kanlon.service;

import com.kanlon.model.CommonResponse;
import com.kanlon.model.ConsumerRequest;
import com.kanlon.proto.GreeterGrpc;
import com.kanlon.proto.GreeterOuterClass;
import io.grpc.Channel;
import net.devh.springboot.autoconfigure.grpc.client.GrpcChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * rpc调用远程方法的service
 *
 * @author zhangcanlong
 * @since 2019-05-04
 **/
@Service
public class GrpcConsumerService {
    private Logger logger = LoggerFactory.getLogger(GrpcConsumerService.class);

    @Autowired
    private GrpcChannelFactory grpcChannelFactory;

    public CommonResponse callMethod(ConsumerRequest request) {
        if ("localhost".equals(request.getProviderName())) {
            logger.info("调用了执行了本地的方法");
            return CommonResponse.succeedResult("调用了本地方法");
        }
        String providerName = "grpc-spring-cloud-provider";
        //TODO 后期提取通道出来
        Channel channel = grpcChannelFactory.createChannel(request.getProviderName());
        GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);
        //构造远程调用的请求参数类
        GreeterOuterClass.ParamRequest paramRequest =
                GreeterOuterClass.ParamRequest.newBuilder().setParam1(request.getParam1()).setParam2(request.getParam2()).build();
        // 远程请求
        GreeterOuterClass.CommonResponseReply response = stub.callMethod(paramRequest);
        return CommonResponse.succeedResult(response.getDataList());
    }
}
