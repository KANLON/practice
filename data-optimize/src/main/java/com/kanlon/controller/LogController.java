package com.kanlon.controller;

import com.kanlon.model.LogPO;
import com.kanlon.model.LogRequest;
import com.kanlon.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * 日志查询
 *
 * @author zhangcanlong
 * @since 2019/4/2 21:01
 **/
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    /**
     * 查询方法一，使用in
     *
     * @param request 请求参数
     * @return 数据集合
     **/
    @GetMapping("/in")
    public HttpEntity<List<LogPO>> getDataMethod1(@Valid LogRequest request) {
        List<LogPO> list = logService.getData1(request);
        for (LogPO po : list) {
            po.add(linkTo(methodOn(LogController.class).getDataMethod1(request)).withSelfRel());
        }
        return new HttpEntity<>(list);
    }

    /**
     * 查询方法二，使用or
     *
     * @param request 请求参数
     * @return 数据集合
     **/
    @GetMapping("/or")
    public HttpEntity<List<LogPO>> getDataMethod2(@Valid LogRequest request) {
        List<LogPO> list = logService.getData2(request);
        for (LogPO po : list) {
            po.add(linkTo(methodOn(LogController.class).getDataMethod2(request)).withSelfRel());
        }
        return new HttpEntity<>(list);
    }

    /**
     * 查询方法三，使用union all
     *
     * @param request 请求参数
     * @return 数据集合
     **/
    @GetMapping("/union-all")
    public HttpEntity<List<LogPO>> getDataMethod3(@Valid LogRequest request) {
        List<LogPO> list = logService.getData3(request);
        for (LogPO po : list) {
            po.add(linkTo(methodOn(LogController.class).getDataMethod3(request)).withSelfRel());
        }
        return new HttpEntity<>(list);
    }

    /**
     * 查询方法四，使用多线程操作
     *
     * @param request 请求参数
     * @return 数据集合
     **/
    @GetMapping("/thread")
    public HttpEntity<List<LogPO>> getDataMethod4(@Valid LogRequest request) throws InterruptedException,
            ExecutionException {
        List<LogPO> list = logService.getData4(request);
        for (LogPO po : list) {
            po.add(linkTo(methodOn(LogController.class).getDataMethod4(request)).withSelfRel());
        }
        return new HttpEntity<>(list);
    }
}
