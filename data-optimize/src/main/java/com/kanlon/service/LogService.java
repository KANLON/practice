package com.kanlon.service;

import com.kanlon.mapper.LogMapper;
import com.kanlon.model.LogPO;
import com.kanlon.model.LogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private AsyncService asyncService;

    /**
     * 使用方法一，in得到数据
     *
     * @param request 条件实体类
     * @return 日志数据集合
     **/
    public List<LogPO> getData1(LogRequest request) {
        this.commonSetDt(request);
        return logMapper.getLogsByCondition1(request);
    }

    /**
     * 使用方法二，or得到数据
     *
     * @param request 条件实体类
     * @return 日志数据集合
     **/
    public List<LogPO> getData2(LogRequest request) {
        this.commonSetDt(request);
        return logMapper.getLogsByCondition2(request);
    }

    /**
     * 使用方法三，union all得到数据
     *
     * @param request 条件实体类
     * @return 日志数据集合
     **/
    public List<LogPO> getData3(LogRequest request) {
        this.commonSetDt(request);
        return logMapper.getLogsByCondition3(request);
    }

    /**
     * 使用方法四，多线程得到数据,
     *
     * @param request 条件实体类
     * @return 日志数据集合
     **/
    public List<LogPO> getData4(LogRequest request) throws InterruptedException, ExecutionException {
        this.commonSetDt(request);
        CountDownLatch latch = new CountDownLatch(3);
        //设置新的条件
        LogRequest[] requests = new LogRequest[2];
        for (int i = 0; i < requests.length; i++) {
            requests[i] = new LogRequest();
            requests[i].setHtmlname(request.getHtmlname());
            requests[i].setLogurl(request.getLogurl());
        }
        requests[0].setDt(getDateStr(request.getDt(), 2));
        requests[1].setDt(getDateStr(request.getDt(), 4));
        //异步执行
        List<LogPO> list = new ArrayList<>(16);
        Future<List<LogPO>> list1 = asyncService.execMapper(request, latch);
        Future<List<LogPO>> list2 = asyncService.execMapper(requests[0], latch);
        Future<List<LogPO>> list3 = asyncService.execMapper(requests[1], latch);
        //等待5秒还没执行完，直接返回
        latch.await(5, TimeUnit.SECONDS);
        list.addAll(list1.get());
        list.addAll(list2.get());
        list.addAll(list3.get());
        return list;
    }

    /**
     * 将日期设置好
     *
     * @param request 需要设置的实体类
     **/
    private void commonSetDt(LogRequest request) {
        request.setDt2(this.getDateStr(request.getDt(), 2));
        request.setDt4(this.getDateStr(request.getDt(), 4));
    }


    /**
     * 获得(DATE_FORMAT)日期字符串
     *
     * @param date   查询日期
     * @param offset 偏移位移
     * @return 日期字符值
     */
    private String getDateStr(String date, int offset) {
        Calendar calc = Calendar.getInstance();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calc.setTime(formatDate.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calc.add(Calendar.DATE, offset);
        Date startDate = calc.getTime();
        return formatDate.format(startDate);
    }


}
