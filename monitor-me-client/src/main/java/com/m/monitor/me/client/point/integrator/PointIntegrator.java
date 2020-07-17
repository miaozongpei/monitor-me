package com.m.monitor.me.client.point.integrator;

import com.alibaba.fastjson.JSON;
import com.m.monitor.me.client.point.collector.MonitorPoint;
import com.m.monitro.me.common.utils.DateUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class PointIntegrator {
    private Map<Long, Map<String,PerformanceNorm>> integratorMap=new LinkedHashMap<>();
    private AtomicLong total= new AtomicLong(0);

    public void put(MonitorPoint point){
        Long second= Long.parseLong(DateUtil.parseSecond(point.getEndTime()));
        get(second,point.getFullMethodName()).add(point);
        total.incrementAndGet();

    }
    public Map<String,PerformanceNorm>  get(Long second){
        Map<String,PerformanceNorm> performanceNormMap=integratorMap.get(second);
        if(performanceNormMap==null){
            performanceNormMap=new HashMap<>();
            integratorMap.put(second,performanceNormMap);
        }
        return performanceNormMap;
    }

    public PerformanceNorm get(Long second,String name){
        Map<String,PerformanceNorm> performanceNormMap=get(second);
        PerformanceNorm performanceNorm=performanceNormMap.get(name);
        if(performanceNorm==null){
            performanceNorm=new PerformanceNorm();
            performanceNormMap.put(name,performanceNorm);
        }
        return performanceNorm;
    }

    public Map<Long, Map<String, PerformanceNorm>> getIntegratorMap() {
        return integratorMap;
    }

    public AtomicLong getTotal() {
        return total;
    }

    @Override
    public String toString() {
        StringBuffer str=new StringBuffer();
        str.append('{');
        for (Map.Entry<Long, Map<String, PerformanceNorm>> entry:integratorMap.entrySet()) {
            str.append(entry.getKey()).append(":[");
            for (Map.Entry<String, PerformanceNorm> pnEntry : entry.getValue().entrySet()) {
                str.append(pnEntry.getKey()).append(':').append(pnEntry.getValue().toString()).append(',');
            }
            str.setLength(str.length()-1);
            str.append("],");
        }
        str.setLength(str.length()-1);
        return str.toString();
    }
}
