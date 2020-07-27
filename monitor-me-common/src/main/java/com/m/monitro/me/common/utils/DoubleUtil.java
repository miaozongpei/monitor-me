package com.m.monitro.me.common.utils;

public class DoubleUtil {
    public static double avg(long l1,long l2){
        double f = l1/l2;
        return Double.parseDouble(String.format("%.2f", f));
    }
}
