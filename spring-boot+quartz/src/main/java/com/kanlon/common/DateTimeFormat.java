package com.kanlon.common;

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
public class DateTimeFormat {
    public static final String NORMAL_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 根据字符串解析标准日期，格式为："yyyy-MM-dd HH:mm:ss"
     * @param s 日期字符串
     * @return java.util.Date
     **/
    public static Date parseIOS(String s) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeFormat.NORMAL_PATTERN);
        Date ret = sdf.parse(s);
        return ret;
    }

    /**
     * 根据时间格式化为标准字符串，格式为："yyyy-MM-dd HH:mm:ss"
     * @param date 时间类
     * @return java.lang.String
     **/
    public static String printIOS(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeFormat.NORMAL_PATTERN);
        String ret = sdf.format(date);
        return ret;
    }

}
