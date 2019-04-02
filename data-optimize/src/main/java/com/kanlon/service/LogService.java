package com.kanlon.service;

import com.kanlon.mapper.LogMapper;
import com.kanlon.model.LogPO;
import com.kanlon.model.LogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日志业务逻辑层
 *
 * @author zhangcanlong
 * @since 2019/4/2 21:07
 **/
@Service
public class LogService {

    @Autowired
    private LogMapper logMapper;

    /**
     * 使用方法一，in得到数据
     * @param request 条件实体类
     * @return 日志数据集合
     **/
    public List<LogPO> getData1(LogRequest request){
        this.commonSetDt(request);
        return logMapper.getLogsByCondition1(request);
    }

    /**
     * 使用方法二，or得到数据
     * @param request 条件实体类
     * @return 日志数据集合
     **/
    public List<LogPO> getData2(LogRequest request){
        this.commonSetDt(request);
        return logMapper.getLogsByCondition2(request);
    }
    /**
     * 使用方法一，in得到数据
     * @param request 条件实体类
     * @return 日志数据集合
     **/
    public List<LogPO> getData3(LogRequest request){
        this.commonSetDt(request);
        return logMapper.getLogsByCondition3(request);
    }
    /**
     * 使用方法一，in得到数据
     * @param request 条件实体类
     * @return 日志数据集合
     **/
    public List<LogPO> getData4(LogRequest request){
        this.commonSetDt(request);
        return logMapper.getLogsByCondition4(request);
    }

    /**
     * 将日期设置好
     * @param request 需要设置的实体类
     * @return void
     **/
    private void commonSetDt(LogRequest request){
        request.setDt2(this.getDateStr(request.getDt(),2));
        request.setDt4(this.getDateStr(request.getDt(),4));
        request.setDt6(this.getDateStr(request.getDt(),6));
    }


    /**
     * 获得(DATE_FORMAT)日期字符串
     *
     * @param date   查询日期
     * @param offset 偏移位移
     * @return 日期字符值
     */
    private  String getDateStr(String date, int offset)  {
        Calendar calc = Calendar.getInstance();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calc.setTime(formatDate.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calc.add(Calendar.DATE, offset);
        Date startDate = calc.getTime();
        return  formatDate.format(startDate);
    }


}
