package com.m.monitro.me.common.utils;

import java.util.UUID;

public class MethodTraceIdUtil {
    private static final String spl=":";
    public static String create(String methodName){
        return methodName + spl + System.currentTimeMillis()+spl+UUID.randomUUID().toString();
    }
    public static String[] split(String tranceId){
        return tranceId.split(spl);
    }
    public static String splitMethodName(String tranceId){
        return split(tranceId)[0];
    }
    public static long splitTime(String tranceId){
         return Long.parseLong(split(tranceId)[1]);
    }
}
