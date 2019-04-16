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
     * 根据其任务id，获取其全部执行日志结果数量
     * @param quartzId 任务id
     * @return java.lang.Long
     **/
    @Select("select count(id) from "+TABLE_NAME+" where quartz_id=#{quartzId}")
    Long selectResultCntByQuartzId(@Param("quartzId") long quartzId);

    /**
     * 获取任务执行日志结果集
     * @param quartzId 任务id
     * @param start 开始条数
     * @param limit 获取条数
     * @return 结果集合
     **/
    @Select("SELECT id,dt,quartz_id,start_time,schedule_result,exec_result,exec_time,complete_time,remark,ctime from " +TABLE_NAME +" where quartz_id=#{quartzId} order by  ctime desc limit #{start},#{limit}")
    List<QuartzResultModel> selectByQuartzId(@Param("quartzId") long quartzId,@Param("start") int start,@Param("limit") int limit);


    /**
     * 根据其任务id和时间条件，获取其全部执行日志结果数量
     * @param quartzId 任务id
     * @param st 开始日期
     * @param et 结束日期
     * @return java.lang.Long
     **/
    @Select("select count(id) from "+TABLE_NAME+" where quartz_id=#{quartzId} and dt>=#{st} and dt<=#{et} ")
    Long selectResultCntByQuartzIdAndDate(@Param("quartzId") long quartzId,@Param("st")String st,@Param("et")String et);

    /**
     * 获取任务执行日志结果集
     * @param quartzId 任务id
     * @param start 开始条数
     * @param limit 获取条数
     * @param st 开始日期
     * @param et 结束日期
     * @return 结果集合
     **/
    @Select("SELECT id,dt,quartz_id,start_time,schedule_result,exec_result,exec_time,complete_time,remark,ctime from " +TABLE_NAME +" where quartz_id=#{quartzId} and dt>=#{st} and dt<=#{et}  order by  ctime desc limit #{start},#{limit}")
    List<QuartzResultModel> selectResultByQuartzIdAndDate(@Param("quartzId") long quartzId,@Param("st")String st,@Param("et")String et,@Param("start") int start,@Param("limit") int limit);




    /**
     * 根据其任务id和时间条件，获取其全部执行日志结果数量
     * @param quartzId 任务id
     * @param st 开始日期
     * @param et 结束日期
     * @param execResult  执行结果，0表示失败，1表示成功，2表示执行中
     * @return java.lang.Long
     **/
    @Select("select count(id) from "+TABLE_NAME+" where quartz_id=#{quartzId} and dt>=#{st} and dt<=#{et} and exec_result=#{execResult}")
    Long selectResultCntByQuartzIdAndDateAndResult(@Param("quartzId") long quartzId,
            @Param("st")String st,
            @Param("et")String et,
            @Param("execResult")Integer execResult);

    /**
     * 获取任务执行日志结果集
     * @param quartzId 任务id
     * @param st 开始日期
     * @param et 结束日期
     * @param execResult  执行结果，0表示失败，1表示成功，2表示执行中
     * @param start 开始条数
     * @param limit 获取条数
     * @return 结果集合
     **/
    @Select("SELECT id,dt,quartz_id,start_time,schedule_result,exec_result,exec_time,complete_time,remark,ctime from " +TABLE_NAME +" where quartz_id=#{quartzId} and dt>=#{st} and dt<=#{et} and exec_result=#{execResult} order by  ctime desc limit #{start},#{limit}")
    List<QuartzResultModel> selectResultByQuartzIdAndDateAndResult(@Param("quartzId") long quartzId,
            @Param("st")String st,
            @Param("et")String et,
            @Param("execResult")Integer execResult,
            @Param("start") int start,
            @Param("limit") int limit);


    /***
     * 插入一条执行记录
     * @param model 插入信息类
     * @return 更新条数
     **/
    @Insert("insert into "+TABLE_NAME+"(dt,quartz_id,start_time,schedule_result,exec_result,exec_time,complete_time,remark,ctime)"
            + " values (#{dt},#{quartzId},#{startTime,jdbcType=TIMESTAMP},#{scheduleResult},#{execResult},#{execTime},#{completeTime,jdbcType=TIMESTAMP},#{remark},#{ctime,jdbcType=TIMESTAMP})")
    Integer insertOne(QuartzResultModel model);

}
