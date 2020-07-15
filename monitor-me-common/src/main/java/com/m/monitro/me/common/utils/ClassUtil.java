package com.m.monitro.me.common.utils;

public class ClassUtil {
    public static String classTypeForStr(Class clazz){
        return clazz.getName().replace(".", "/");
    }
}
