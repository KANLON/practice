package com.kanlon.service;

import com.kanlon.mapper.QuartzResultMapper;
import com.kanlon.model.PageModel;
import com.kanlon.model.QuartzResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * @param pageModel 分页参数
     * @return 结果集
     **/
    public List<QuartzResultModel> getQuartzResultByQuartzId(long quartzId, PageModel pageModel){
        return quartzResultMapper.selectByQuartzId(quartzId,pageModel.getStart(),pageModel.getPageSize());
    }

    /**
     * 增加某个任务的执行结果
     * @param model 执行结果信息
     **/
    public void addQuartzResult(QuartzResultModel model){
        quartzResultMapper.insertOne(model);
    }

}
