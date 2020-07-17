package com.m.monitor.me.client.point.collector;

import com.alibaba.fastjson.JSON;
import com.m.monitor.me.client.point.integrator.PointIntegrator;
import com.m.monitor.me.client.transfer.client.MonitorExpressWayClient;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class MonitorPointCollector {
    public static Map<String, MonitorPoint> pointMap = new ConcurrentHashMap<>();

    public static List<PointIntegrator> pointIntegrators=new ArrayList<>();
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
        createOrGetIntegrator().put(point);
        //移除收集器
        pointMap.remove(tranceId);
    }

    public static PointIntegrator createOrGetIntegrator(){
        PointIntegrator integrator=pointIntegrators.get(pointIntegrators.size()-1);
        int len=integrator.getTotal().intValue();
        if (len>=10){
            MonitorExpressWayClient.getInstance().send(JSON.toJSONString(integrator));
            integrator=new PointIntegrator();
            pointIntegrators.add(integrator);
        }
        return integrator;
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

