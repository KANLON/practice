package com.kanlon.service;

import com.kanlon.mapper.LogMapper;
import com.kanlon.model.LogPO;
import com.kanlon.model.LogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * 异步方法操作类
 *
 * @author zhangcanlong
 * @since 2019/5/8 9:22
 **/
@Component
public class AsyncService {
    @Autowired
    private LogMapper logMapper;

    /**
     * 异步执行mapper
     *
     * @param request 条件
     * @return 执行返回的实体类
     **/
    @Async("taskExecutor")
    public Future<List<LogPO>> execMapper(LogRequest request, CountDownLatch latch) {
        Long time1 = System.currentTimeMillis();
        AsyncResult<List<LogPO>> asyncResult = new AsyncResult<>(this.logMapper.getLogsByCondition4(request));
        System.out.println("线程名为：" + Thread.currentThread().getName());
        System.out.println("花费时间为：" + (System.currentTimeMillis() - time1));
        latch.countDown();
        return asyncResult;
    }
}
