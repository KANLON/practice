package com.kanlon.common;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 格式化日期时间
 * @author zhangcanlong
 * @since 2019-04-15 15:59
 */
@Service
public class DateTimeFormat implements Formatter<Date> {
    public static final String NORMAL_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";


    @Override
    public Date parse(String s, Locale locale) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeFormat.NORMAL_PATTERN);
        sdf.setTimeZone(TimeZone.getTimeZone(Constant.TIMEZONE_STR));
        Date ret = sdf.parse(s);
        return ret;
    }

    @Override
    public String print(Date date, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeFormat.NORMAL_PATTERN);
        sdf.setTimeZone(TimeZone.getTimeZone(Constant.TIMEZONE_STR));
        String ret = sdf.format(date);
        return ret;
    }

    /**
     * 根据字符串解析标准日期，格式为："yyyy-MM-dd HH:mm:ss"
     * @param s 日期字符串
     * @return java.util.Date
     **/
    public static Date parseIOS(String s) throws ParseException {
        return new DateTimeFormat().parse(s,Locale.SIMPLIFIED_CHINESE);
    }

    /**
     * 根据时间格式化为标准字符串，格式为："yyyy-MM-dd HH:mm:ss"
     * @param date 时间类
     * @return java.lang.String
     **/
    public static String printIOS(Date date) {
        return new DateTimeFormat().print(date,Locale.SIMPLIFIED_CHINESE);
    }
    /**
     * 根据字符串解析本地日期，格式为："yyyy-MM-dd"
     * @param s 日期字符串
     * @return java.util.Date
     **/
    public static Date parseLocal(String s) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeFormat.DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone(Constant.TIMEZONE_STR));
        Date ret = sdf.parse(s);
        return ret;
    }

    /**
     * 根据时间格式化为本地日期字符串，格式为："yyyy-MM-dd"
     * @param date 时间类
     * @return java.lang.String
     **/
    public static String printLocal(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeFormat.DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone(Constant.TIMEZONE_STR));
        String ret = sdf.format(date);
        return ret;
    }
}
