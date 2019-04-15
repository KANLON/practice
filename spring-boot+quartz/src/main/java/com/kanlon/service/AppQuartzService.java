package com.kanlon.service;

import com.kanlon.exception.QuartzException;
import com.kanlon.mapper.QuartzCronMapper;
import com.kanlon.model.AppQuartz;
import com.kanlon.model.PageModel;
import com.kanlon.model.QuartzInfo;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

/**
 * 调度工作操作service
 * @author zhangcanlong
 * @since 2019/4/9 21:37
 **/
@Service
public class AppQuartzService {
    @Autowired
    private JobUtil jobUtil;

    @Autowired
    private QuartzCronMapper cronMapper;

    private Logger logger = LoggerFactory.getLogger(AppQuartzService.class);

    /**
     * 从自己创建的表格中获取所有任务，包含任务id
     * @return AppQuartz集合
     **/
    public List<QuartzInfo> getAllTaskFromMyTable(PageModel page){
        return cronMapper.selectAllJob(page.getStart(),page.getPageSize());
    }
    /**
     * 插入新的任务调度
     * @param appQuartz 新任务实体类
     **/
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public void insertAppQuartzSer(AppQuartz appQuartz) throws Exception {
        cronMapper.insertOne(appQuartz);
        jobUtil.addJob(appQuartz);
        logger.info("添加自己的quartz信息成功");
    }

    /**
     * 选择某个任务
     * @param quartzId 根据任务id
     * @return com.kanlon.model.AppQuartz
     **/
    public AppQuartz selectAppQuartzByIdSer(Integer quartzId) {
        AppQuartz appQuartz = cronMapper.selectTaskById(quartzId);
        if(appQuartz==null){
            throw new QuartzException("找不到该id的任务");
        }
        return appQuartz;
    }

    /**
     * 删除任务
     * @param quartzId 任务id
     **/
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public void deleteAppQuartzByIdSer(Integer quartzId) throws SchedulerException {
        AppQuartz appQuartz  = this.selectAppQuartzByIdSer(quartzId);
        jobUtil.deleteJob(appQuartz.getJobName(),appQuartz.getJobGroup());
        cronMapper.deleteAppQuartzByIdSer(quartzId);
    }

    /**
     * 更新任务
     * @param appQuartz 根据任务实体类信息
     **/
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public void updateAppQuartzSer(AppQuartz appQuartz) throws SchedulerException, ParseException {
        AppQuartz oldAppQuartz = this.selectAppQuartzByIdSer(appQuartz.getQuartzId());
        if(oldAppQuartz==null){
            throw new QuartzException("该任务id不存在，不能执行更新操作");
        }
        appQuartz.setJobName(oldAppQuartz.getJobName());
        appQuartz.setJobGroup(oldAppQuartz.getJobGroup());
        jobUtil.modifyJob(appQuartz);
        cronMapper.updateAppQuartzSer(appQuartz);
        logger.info("修改自己建立的辅助quartz信息成功");
    }


}
