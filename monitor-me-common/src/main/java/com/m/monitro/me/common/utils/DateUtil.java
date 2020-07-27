package com.m.monitro.me.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /**
     * 日期格式，年月日时分秒，例如：20001230120000，20080808200808
     */
    public static final String FORMAT_YYYYMMDDHHMISS = "yyyyMMddHHmmss";

    public static final String FORMAT_YYYYMM = "yyyyMMdd";


    public static String parseDate(Date time, String format){
        DateFormat dateFormat=new SimpleDateFormat(format);
        return dateFormat.format(time);
    }

    public static String parseSecond(long time){
        return parseDate(new Date(time),FORMAT_YYYYMMDDHHMISS);
    }
}
