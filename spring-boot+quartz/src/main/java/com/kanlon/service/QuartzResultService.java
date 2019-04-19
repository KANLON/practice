package com.kanlon.service;

import com.kanlon.mapper.QuartzResultMapper;
import com.kanlon.model.PageDatasModel;
import com.kanlon.model.PageModel;
import com.kanlon.model.QuartzResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 执行日志结果service
 *
 * @author zhangcanlong
 * @since 2019/4/15 21:08
 **/
@Service
public class QuartzResultService {

    @Autowired
    private QuartzResultMapper quartzResultMapper;

    /**
     * 根据任务id，获取执行记录
     * @param quartzId 任务id
     * @param st 开始日期
     * @param et 结束日期
     * @param execResult 执行结果
     * @param pageModel 分页参数
     * @return 结果集
     **/
    public PageDatasModel getQuartzResultByQuartzIdAndResult(long quartzId,String st,String et,Integer execResult, PageModel pageModel){
        PageDatasModel pageDatasModel = new PageDatasModel(pageModel);
        pageDatasModel.setTotalSize(quartzResultMapper.selectResultCntByQuartzIdAndDateAndResult(quartzId,st,et,execResult));
        pageDatasModel.setDatas(quartzResultMapper.selectResultByQuartzIdAndDateAndResult(quartzId,st,et,execResult,pageModel.getStart(),pageModel.getPageSize()));
        return pageDatasModel;
    }

    /**
     * 根据任务id，获取执行记录
     * @param quartzId 任务id
     * @param st 开始日期
     * @param et 结束日期
     * @param pageModel 分页参数
     * @return 结果集
     **/
    public PageDatasModel getQuartzResultByQuartzId(long quartzId,String st,String et, PageModel pageModel){
        PageDatasModel pageDatasModel = new PageDatasModel(pageModel);
        pageDatasModel.setTotalSize(quartzResultMapper.selectResultCntByQuartzIdAndDate(quartzId,st,et));
        pageDatasModel.setDatas(quartzResultMapper.selectResultByQuartzIdAndDate(quartzId,st,et,pageModel.getStart(),pageModel.getPageSize()));
        return pageDatasModel;
    }

    /**
     * 增加某个任务的执行结果
     * @param model 执行结果信息
     **/
    public void addQuartzResult(QuartzResultModel model){
        quartzResultMapper.insertOne(model);
    }

}
