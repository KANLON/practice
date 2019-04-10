package com.kanlon.service;

import com.kanlon.mapper.QuartzCronMapper;
import com.kanlon.model.AppQuartz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangcanlong
 * @since 2019/4/9 21:37
 **/
@Service
public class AppQuartzService {

    @Autowired
    private QuartzCronMapper cronMapper;

    public void insertAppQuartzSer(AppQuartz appQuartz) {
        this.cronMapper.insertOne(appQuartz);
    }

    public AppQuartz selectAppQuartzByIdSer(Integer quartzId) {

        return cronMapper.selectTaskById(quartzId);
    }

    public void deleteAppQuartzByIdSer(Integer quartzId) {
         cronMapper.deleteAppQuartzByIdSer(quartzId);
    }

    public void updateAppQuartzSer(AppQuartz appQuartz) {
        cronMapper.updateAppQuartzSer(appQuartz);
    }
}
