package com.m.monitro.me.common.utils;

import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;

import java.util.Calendar;

public class MonitorTimeUtil {
    //20200821141212
    public static Long toTime(Long time, MonitorTimeUnitEnum unitEnum){
        switch (unitEnum){
            case DAY: {
                return Long.parseLong((time / 1000000)+"000000");
            }
            case HOUR: {
                return Long.parseLong((time / 10000)+"0000");
            }
            case MINUTE: {
                return Long.parseLong((time / 100)+"00");
            }
        }
        return time;
    }
    public static Long subTime(Long time, int num,MonitorTimeUnitEnum unitEnum){
        long afterTime=DateUtil.parse(time+"",DateUtil.FORMAT_YYYYMMDDHHMISS).getTime()-num*unitEnum.unit*1000;
        return Long.parseLong(DateUtil.formatSecond(afterTime));
    }
}
