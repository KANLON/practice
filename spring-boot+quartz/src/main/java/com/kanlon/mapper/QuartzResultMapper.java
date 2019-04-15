package com.kanlon.mapper;

import com.kanlon.model.QuartzResultModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 任务执行日志结果表操作mapper
 *
 * @author zhangcanlong
 * @since 2019/4/15 20:54
 **/
@Mapper
public interface QuartzResultMapper {
    String TABLE_NAME = " tb_quartz_result ";

    /**
     * 获取任务执行日志结果集
     * @param quartzId 任务id
     * @param start 开始条数
     * @param limit 获取条数
     * @return 结果集合
     **/
    @Select("selet * from " +TABLE_NAME +" where quartz_id=#{quartzId} order by  ctime desc limit #{start},#{limit}")
    List<QuartzResultModel> selectByQuartzId(@Param("quartzId") long quartzId,@Param("start") int start,@Param("limit") int limit);

    /***
     * 插入一条执行记录
     * @param model 插入信息类
     * @return 更新条数
     **/
    @Insert("insert into "+TABLE_NAME+"(quartzId,startTime,scheduleResult,execResult,execTime,completeTime,remark,ctime)"
            + " values (#{quartzId},#{startTime},#{scheduleResult},#{execResult},#{execTime},#{completeTime},#{remark},#{ctime})")
    Integer insertOne(QuartzResultModel model);

}
