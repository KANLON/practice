package com.kanlon.mapper;

import com.kanlon.model.AppQuartz;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 操作quartz的core信息的mapper
 *
 * @author zhangcanlong
 * @since 2019/4/9 15:39
 **/
@Mapper
public interface QuartzCronMapper {

    String TABLE_NAME = " tb_app_quartz ";
    /**
     * 选择所有任务
     * @return 返回任务集合
     **/
    @Select("select * from "+TABLE_NAME)
    List<AppQuartz> selectAllTask();

    /**
     * 选择特定id的任务信息
     * @return 返回任务信息
     **/
    @Select("select * from "+TABLE_NAME +" where quartz_id=#{id}")
    AppQuartz selectTaskById(Integer id);

    /**
     * 插入一个新任务
     * @param quartz 任务信息
     * @return 插入的行数
     **/
    @Insert("insert into "+TABLE_NAME+"(job_name,job_group,start_time,cron_expression,invoke_param) values (#{jobName},#{jobGroup},#{startTime},#{cronExpression},#{invokeParam})")
    Integer insertOne(AppQuartz quartz);

    /***
     * 根据id删除任务
     * @param id 根据id删除任务
     * @return java.lang.Integer 删除行数
     **/
    @Delete("delete from "+ TABLE_NAME + " where quartz_id=#{id}")
    Integer deleteAppQuartzByIdSer(Integer id);

    /**
     * 根据新的任务信息更新任务
     * @param appQuartz 新的任务信息
     * @return 更新的行数
     **/
    @Update("UPDATE tb_app_quartz  "
            + "SET job_name=#{jobName},job_group=#{jobGroup},start_time=#{startTime},cron_expression=#{cronExpression},invoke_param=#{invokeParam}"
            + "WHERE quartz_id=#{quartzId}")
    Integer updateAppQuartzSer(AppQuartz appQuartz);

}
