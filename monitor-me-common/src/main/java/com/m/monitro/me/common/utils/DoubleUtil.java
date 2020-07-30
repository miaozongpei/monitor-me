package com.m.monitro.me.common.utils;

public class DoubleUtil {
    public static double avg(long l1,long l2){
        double f = (double) l1/l2;
        return avg((double) l1,l2);
    }
    public static double avg(double d1,long l2){
        double f = d1/l2;
        return Double.parseDouble(String.format("%.2f", f));
    }

    public static void main(String[] args) {
        System.out.println(avg(312,23));
    }
}
