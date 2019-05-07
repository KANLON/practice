package com.kanlon.mapper;

import com.kanlon.model.LogPO;
import com.kanlon.model.LogRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 日志查询表，数据查询
 *
 * @author zhangcanlong
 * @since 2019/4/2 20:58
 **/
@Mapper
public interface LogMapper {
    /**
     * 表名
     */
    String TABLE_NAME = "logs1";

    String COMMON_SQL =  "select * from " +TABLE_NAME+" d where d.htmlname=#{htmlname} and  d.logurl=#{logurl} ";
    /**
     * 方法一，使用in进行查询
     * @param condition 条件实体类
     * @return 集合
     **/
    @Select("select * from " +TABLE_NAME+" d where d.htmlname=#{htmlname} and  d.logurl=#{logurl} and d.dt in (#{dt},#{dt2},#{dt4},#{dt6})")
    List<LogPO> getLogsByCondition1(LogRequest condition);
    /**
     * 方法二，使用or进行查询
     * @param condition 条件实体类
     * @return 集合
     **/
    @Select("select * from " +TABLE_NAME+" d where d.htmlname=#{htmlname} and  d.logurl=#{logurl} and (d.dt=#{dt} or d.dt=#{dt2} or d.dt=#{dt4} or d.dt=#{dt6})")
    List<LogPO> getLogsByCondition2(LogRequest condition);
    /**
     * 方法三，使用union all进行查询
     * @param condition 条件实体类
     * @return 集合
     **/
    @Select( COMMON_SQL+ " and d.dt=#{dt} union all "
            +COMMON_SQL+ " and d.dt=#{dt2} union all "
            +COMMON_SQL+ " and d.dt=#{dt4} union all "
            +COMMON_SQL+ " and d.dt=#{dt6}")
    List<LogPO> getLogsByCondition3(LogRequest condition);
    /**
     * 方法四，使用多线程单个查询
     * @param condition 条件实体类
     * @return 集合
     **/
    @Select("select * from " + TABLE_NAME + " d where d.htmlname=#{htmlname} and  d.logurl=#{logurl} and d.dt=#{dt} ")
    List<LogPO> getLogsByCondition4(LogRequest condition);

}
