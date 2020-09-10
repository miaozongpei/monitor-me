package com.m.monitor.me.client.point.collector;

import com.alibaba.fastjson.JSON;
import com.m.monitor.me.client.point.integrator.PointIntegrator;
import com.m.monitro.me.common.utils.DateUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Slf4j
public class MonitorPointCollector {
    public static Map<String, MonitorPoint> pointMap = new ConcurrentHashMap<>();

    public static List<PointIntegrator> pointIntegrators=new ArrayList<>();

    public static Map<String, String> methodChainMap=new HashMap<>();

    static {
        pointIntegrators.add(new PointIntegrator());
    }
    public static void create(String tranceId, String fullMethodName) {
        pointMap.put(tranceId, new MonitorPoint(fullMethodName, System.currentTimeMillis()));
    }

    public static void finished(String tranceId){
        MonitorPoint point=pointMap.get(tranceId);
        //监控点结束
        point.finished();
        //放入聚合器
        createOrGetIntegrator(point).put(point);

        //放入方法连
        methodChainMap.put(point.getFullMethodName(), point.toString());

        //移除收集器
        pointMap.remove(tranceId);
    }

    public static PointIntegrator createOrGetIntegrator(MonitorPoint point){
        PointIntegrator integrator=pointIntegrators.get(pointIntegrators.size()-1);
        int len=integrator.getTotal().intValue();
        Long second= Long.parseLong(DateUtil.formatSecond(point.getEndTime()));
        //if (len>=2000&&integrator.get(second)==null){
        //5秒新建一个聚合器
        if(integrator.getIntegratorMap().size()==5&&integrator.get(second)==null){
            integrator=new PointIntegrator();
            pointIntegrators.add(integrator);
        }
        return integrator;
    }

    public static synchronized void remove(PointIntegrator pointIntegrator){
        pointIntegrators.remove(pointIntegrator);
    }

    public static void printPointIntegrator(){
       for(PointIntegrator integrator:pointIntegrators){
           System.out.println(JSON.toJSON(integrator));
       }
    }

    public static void printAll(){
        for (MonitorPoint point:pointMap.values()){
            point.print();
        }
    }
}

