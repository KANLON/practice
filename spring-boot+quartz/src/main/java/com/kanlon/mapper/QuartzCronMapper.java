package com.kanlon.mapper;

import com.kanlon.model.AppQuartz;
import com.kanlon.model.QuartzInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

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

    /***
     * 返回所有任务信息
     * @param start  开始条数
     * @param limit 获取条数
     * @return 任务信息集合
     **/
    @Select("SELECT tb.`quartz_id`,tb.`job_name`,tb.`job_group`,tb.`invoke_param`,tb.`mtime`,"
            + " tb.`start_time`, tb.`cron_expression`, tb.`charge`,tb.`charge_department`,tb.`invoke_param2`,"
            + " qrtz.`description`,qrtz.`next_fire_time`,qrtz.`trigger_state`"
            + " FROM tb_app_quartz tb"
            + " LEFT JOIN qrtz_triggers qrtz ON tb.`job_name`=qrtz.`job_name` AND tb.`job_group`=qrtz.`job_group`"
            + " WHERE tb.`dr`=0 order by tb.mtime desc limit #{start},#{limit} ")
    List<QuartzInfo> selectAllJob(@Param("start")  int start,@Param("limit") int limit);

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
    AppQuartz selectTaskById(Long id);

    /**
     * 插入一个新任务
     * @param quartz 任务信息
     * @return 插入的行数
     **/
    @Insert("insert into "+TABLE_NAME+"(job_name,job_group,charge,charge_department,start_time,cron_expression,invoke_param,invoke_param2,ctime,mtime)" +
            " values (#{jobName},#{jobGroup},#{charge},#{chargeDepartment},#{startTime,jdbcType=TIMESTAMP},#{cronExpression},#{invokeParam},#{invokeParam2},#{ctime,jdbcType=TIMESTAMP},#{mtime,jdbcType=TIMESTAMP})")
    @Options(useGeneratedKeys = true, keyProperty = "quartzId")
    Integer insertOne(AppQuartz quartz);

    /***
     * 根据id删除任务
     * @param id 根据id删除任务
     * @return java.lang.Integer 删除行数
     **/
    @Delete("delete from "+ TABLE_NAME + " where quartz_id=#{id}")
    Integer deleteAppQuartzByIdSer(Long id);

    /**
     * 根据新的任务信息更新任务
     * @param appQuartz 新的任务信息
     * @return 更新的行数
     **/
    @SelectProvider(type = AppQuartzSqlProvider.class,method = "updateByConditionSql")
    Integer updateAppQuartzSer(AppQuartz appQuartz);


    /**
     * 动态sql的提供类
     *
     * @author zhangcanlong
     **/
    class AppQuartzSqlProvider {

        /**
         * 根据条件筛选
         *
         * @param condition 实例条件
         * @return java.lang.String
         **/
        public String updateByConditionSql(AppQuartz condition) {
            return new SQL() {{
                UPDATE(TABLE_NAME);
                if (condition.getCharge() != null) {
                    SET("charge=#{charge}");
                }
                if(condition.getChargeDepartment()!=null){
                    SET("charge_department=#{chargeDepartment}");
                }
                if(condition.getStartTime()!=null){
                    SET("start_time=#{startTime,jdbcType=TIMESTAMP}");
                }
                if(condition.getCronExpression()!=null){
                    SET("cron_expression=#{cronExpression}");
                }
                if(condition.getInvokeParam()!=null){
                    SET("invoke_param=#{invokeParam}");
                }
                if(condition.getInvokeParam2()!=null){
                    SET("invoke_param2=#{invokeParam2}");
                }
                if(condition.getMtime()!=null){
                    SET("mtime=#{mtime,jdbcType=TIMESTAMP}");
                }
                WHERE("quartz_id=#{quartzId}");
            }}.toString();
        }
    }

}
