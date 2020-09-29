package com.m.monitor.me.client.point.limit;

import com.m.monitro.me.common.limit.PointLimit;

import java.util.HashMap;
import java.util.Map;

public class PointLimitConfig {
    public static Map<String, PointLimit> limitConfig=new HashMap<>();

    public static PointLimit get(String method){
        PointLimit pointLimit=limitConfig.get(method);
        if (pointLimit==null){
            pointLimit=new PointLimit();
        }
        return pointLimit;
    }
}
