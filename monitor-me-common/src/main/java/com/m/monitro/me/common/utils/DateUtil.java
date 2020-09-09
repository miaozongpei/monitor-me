package com.m.monitro.me.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /**
     * 日期格式，年月日时分秒，例如：20001230120000，20080808200808
     */
    public static final String FORMAT_YYYYMMDDHHMISS = "yyyyMMddHHmmss";

    public static final String FORMAT_YYYYMMDDHHMISSSSS = "yyyy-MM-dd HH:mm:ss:SSS";


    public static final String FORMAT_YYYYMM = "yyyyMMdd";


    public static String format(Date date, String format){
        DateFormat dateFormat=new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
    public static String format(long time, String format){
        DateFormat dateFormat=new SimpleDateFormat(format);
        return dateFormat.format(new Date(time));
    }
    public static String formatSecond(long time){
        return format(new Date(time),FORMAT_YYYYMMDDHHMISS);
    }
    public static Date parse(String time,String format){
        DateFormat dateFormat=new SimpleDateFormat(format);
        try {
            return dateFormat.parse(time);
        } catch (ParseException e) {
           return null;
        }
    }
}
