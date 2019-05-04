package com.linshen.grpcspringcloudprovider;

import com.linshen.grpc.cloud.lib.GreeterGrpc;
import com.linshen.grpc.cloud.lib.GreeterOuterClass;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;

@Slf4j
@GrpcService(GreeterOuterClass.class)
public class GreeterService extends GreeterGrpc.GreeterImplBase {
    @Override
    public void sayHello(GreeterOuterClass.HelloRequest request, StreamObserver<GreeterOuterClass.HelloReply> responseObserver) {
        String message1 = "参数1： " + request.getParam1();
        String message2 = "参数2： " + request.getParam1();
        final GreeterOuterClass.HelloReply helloReply = GreeterOuterClass.HelloReply.newBuilder()
                .setCode(1)
                .setMessage("")
                .addData(message1)
                .addData(message2)
                .build();
        responseObserver.onNext(helloReply);
        responseObserver.onCompleted();
        log.info("Returning " +message1+"\n"+message2);
    }
}
