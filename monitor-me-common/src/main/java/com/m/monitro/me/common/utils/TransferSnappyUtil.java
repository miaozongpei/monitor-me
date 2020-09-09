package com.m.monitro.me.common.utils;

import org.xerial.snappy.Snappy;

public class TransferSnappyUtil {
    public static String compressToStr(String data)throws Exception{
        return HexUtil.byteArrToHex(Snappy.compress(data));
    }
    public static String uncompressToStr(String hexStr)throws Exception{
        return Snappy.uncompressString(HexUtil.hexToByteArr(hexStr));
    }

    public static byte[] compress(String data)throws Exception{
        return Snappy.compress(data);
    }
    public static byte[] uncompress(byte[] data)throws Exception{
        return Snappy.uncompress(data);
    }
}
