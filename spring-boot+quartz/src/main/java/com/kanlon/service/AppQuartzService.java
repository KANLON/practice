package com.kanlon.service;

import com.kanlon.mapper.QuartzCronMapper;
import com.kanlon.model.AppQuartz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 调度工作操作service
 * @author zhangcanlong
 * @since 2019/4/9 21:37
 **/
@Service
public class AppQuartzService {

    @Autowired
    private QuartzCronMapper cronMapper;

    /**
     * 从自己创建的表格中获取所有任务，包含任务id
     * @return AppQuartz集合
     **/
    public List<AppQuartz> getAllTaskFromMyTable(){
        return cronMapper.selectAllTask();
    }
    /**
     * 插入新的任务调度
     * @param appQuartz 新任务实体类
     **/
    public void insertAppQuartzSer(AppQuartz appQuartz) {
        this.cronMapper.insertOne(appQuartz);
    }

    /**
     * 选择某个任务
     * @param quartzId 根据任务id
     * @return com.kanlon.model.AppQuartz
     **/
    public AppQuartz selectAppQuartzByIdSer(Integer quartzId) {
        return cronMapper.selectTaskById(quartzId);
    }

    /**
     * 删除任务
     * @param quartzId 任务id
     **/
    public void deleteAppQuartzByIdSer(Integer quartzId) {
         cronMapper.deleteAppQuartzByIdSer(quartzId);
    }

    /**
     * 更新任务
     * @param appQuartz 根据任务实体类信息
     **/
    public void updateAppQuartzSer(AppQuartz appQuartz) {
        cronMapper.updateAppQuartzSer(appQuartz);
    }
}
