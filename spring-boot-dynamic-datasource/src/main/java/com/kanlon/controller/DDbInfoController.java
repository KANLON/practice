package com.kanlon.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kanlon.model.DDbInfo;
import com.kanlon.response.CommonResponse;
import com.kanlon.service.IDDbInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.validation.Valid;

/**
 * <p>
 * 张灿龙,2019-07-17 10:30。说明：数据库信息 前端控制器
 * </p>
 *
 * @author zhangcanlon
 * @since 2019-09-01
 */
@RestController
@RequestMapping("/dDbInfo")
public class DDbInfoController {

    @Autowired
    private IDDbInfoService idDbInfoService;

    /**
     * 获取全部db信息
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @return com.kanlon.response.CommonResponse
     **/
    @GetMapping("/list")
    public CommonResponse getDbInfoList(@RequestParam(name = "pageNo",defaultValue = "1") Long pageNo,
            @RequestParam(name = "pageSize",defaultValue = "10") Long pageSize){
        Page<DDbInfo> page = new Page<>();
        page.setPages(pageNo);
        page.setSize(pageSize);
        return CommonResponse.succeedResult(idDbInfoService.page(page));
    }

    /**
     * 保存一个数据库信息
     *
     * @param info 数据库信息
     * @return com.kanlon.response.CommonResponse
     **/
    @PostMapping("/dbInfo")
    public CommonResponse addDbInfo(@Valid @RequestBody DDbInfo info){
        idDbInfoService.save(info);
        return CommonResponse.succeedResult();
    }

    /**
     * 删除一个数据库信息
     *
     * @param id 数据库信息id
     * @return com.kanlon.response.CommonResponse
     **/
    @DeleteMapping("/dbInfo")
    public CommonResponse deleteDbInfo(@RequestParam(name = "id") Long id){
        idDbInfoService.removeById(id);
        return CommonResponse.succeedResult();
    }
}

