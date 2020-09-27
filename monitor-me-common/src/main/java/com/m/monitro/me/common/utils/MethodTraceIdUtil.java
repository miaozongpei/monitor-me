package com.m.monitro.me.common.utils;

import java.util.UUID;

public class MethodTraceIdUtil {
    private static final String SPL=":";
    public static String create(String methodName){
        return methodName + SPL + System.currentTimeMillis()+SPL+UUID.randomUUID().toString();
    }
    public static String[] split(String tranceId){
        return tranceId.split(SPL);
    }
    public static String splitMethodName(String tranceId){
        return split(tranceId)[0];
    }
    public static long splitTime(String tranceId){
         return Long.parseLong(split(tranceId)[1]);
    }
}
