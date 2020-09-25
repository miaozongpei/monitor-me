package com.m.monitor.me.client.point.limit;

import com.m.monitro.me.common.limit.PointLimit;

import java.util.HashMap;
import java.util.Map;

public class PointLimitConfig {
    public static Map<String, PointLimit> limitConfig=new HashMap<>();
    static {
        PointLimit pointLimit=new PointLimit();
        pointLimit.setSleepMillis(1000);
        limitConfig.put("com.m.monitor.me.example.service.impl.DemoServiceImpl.findUserByName",pointLimit);
    }
    public static void put(String method,PointLimit pointLimit){
        limitConfig.put(method,pointLimit);
    }

    public static PointLimit get(String method){
        PointLimit pointLimit=limitConfig.get(method);
        if (pointLimit==null){
            pointLimit=new PointLimit();
            limitConfig.put(method,pointLimit);
        }
        return pointLimit;
    }
}
