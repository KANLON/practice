package com.kanlon.service;


import com.kanlon.proto.GreeterGrpc;
import com.kanlon.proto.GreeterOuterClass;
import io.grpc.stub.StreamObserver;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * grpc服务端提供的service
 *
 * @author zhangcanlong
 * @since 2019/4/25 9:12
 **/
@GrpcService(GreeterOuterClass.class)
public class GrpcServerService extends GreeterGrpc.GreeterImplBase {

    private Logger logger = LoggerFactory.getLogger(GrpcServerService.class);

    /**
     * 提供的方法
     *
     * @param request          请求
     * @param responseObserver 返回
     **/
    @Override
    public void callMethod(GreeterOuterClass.ParamRequest request,
            StreamObserver<GreeterOuterClass.CommonResponseReply> responseObserver) {
        String message1 = "参数1, " + request.getParam1();
        String message2 = "参数2, " + request.getParam2();
        final GreeterOuterClass.CommonResponseReply helloReply =
                GreeterOuterClass.CommonResponseReply.newBuilder().setCode(1).setMessage("").addData(message1).addData(message2).build();
        responseObserver.onNext(helloReply);
        responseObserver.onCompleted();
        logger.info("Returning " + message1 + "\n" + message2);
        super.callMethod(request, responseObserver);
    }

}
