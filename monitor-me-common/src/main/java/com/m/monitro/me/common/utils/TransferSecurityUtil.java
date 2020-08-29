package com.m.monitro.me.common.utils;

public class TransferSecurityUtil {
    private static String slat="21%^&*(3424&*()ghjHJK&*";
    public static String sign(String content){
        return MD5Util.encrypt(content,slat);
    }
    public static boolean validateSign(String sign, String content){
        return MD5Util.encrypt(content,slat).equals(sign);
    }
}
