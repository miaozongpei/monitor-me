package com.m.monitor.me.client.handler.point;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class MonitorPointContext {
    public static Map<String, MonitorPoint> pointMap = new ConcurrentHashMap<>();

    public static void create(String tranceId, String fullMethodName) {
        pointMap.put(tranceId, new MonitorPoint(fullMethodName, System.currentTimeMillis()));
    }



    public static void printAll(){
        for (MonitorPoint point:pointMap.values()){
            point.print();
        }
    }
}

