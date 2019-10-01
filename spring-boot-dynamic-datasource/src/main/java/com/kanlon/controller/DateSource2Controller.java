package com.kanlon.controller;

import com.kanlon.model.DDbInfo;
import com.kanlon.request.ExecSqlRequest;
import com.kanlon.response.CommonResponse;
import com.kanlon.service.IDDbInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 动态数据源方法二，将数据源放到自己定义的统一变量中
 *
 * @author zhangcanlong
 * @since 2019/10/1 15:15
 **/
@RestController
@RequestMapping("datasource2")
public class DateSource2Controller {

    @Autowired
    private IDDbInfoService iDbInfoService;

    /**
     * 增加或更新数据源
     *
     * @param dDbInfo 要增加/更新的增加数据源的信息
     * @return com.kanlon.response.CommonResponse
     **/
    @PostMapping("/add-or-update/info")
    public CommonResponse addDataSource(@RequestBody @Valid DDbInfo dDbInfo){
        dDbInfo.setUsername("admin");
        iDbInfoService.addOrUpdateDataSource(dDbInfo);
        return CommonResponse.succeedResult();
    }

    /**
     * 执行sql的信息
     *
     * @param execSqlRequest 执行的sql的请求参数
     * @return com.kanlon.response.CommonResponse
     **/
    @PostMapping("/exec-sql")
    public CommonResponse execSql(@RequestBody @Valid ExecSqlRequest execSqlRequest){
        return CommonResponse.succeedResult(iDbInfoService.execSqlWithSelfDataSource(execSqlRequest.getDbId(),
                execSqlRequest.getSql()));
    }

}
