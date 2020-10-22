package com.m.monitor.me.admin.auth.login.face.baidu;

import com.baidu.aip.face.AipFace;

public class BaiduApi{
    private static  String APP_ID = "11442925";
    private static  String API_KEY = "nivLvjKlQe2FpA0qnMfWVZZg";
    private static  String SECRET_KEY = "ySGSdwi5gXzzGG0LhxRGhuWfpNGdc9ys";

    private static BaiduApiFace faceClient;

    public static BaiduApiFace newFaceClient(){
        if (faceClient==null){
            faceClient= new BaiduApiFace(APP_ID, API_KEY, SECRET_KEY);
            // 可选：设置网络连接参数
            faceClient.setConnectionTimeoutInMillis(2000);
            faceClient.setSocketTimeoutInMillis(60000);
        }
        return faceClient;
    }


}
