package com.m.monitor.me.client.point.collector;

import com.alibaba.fastjson.JSON;
import com.m.monitor.me.client.point.integrator.PointIntegrator;
import com.m.monitro.me.common.utils.DateUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 监控点收集器
 * @Author: miaozp
 * @Date: 2020/10/31 5:42 下午
 **/
@Getter
@Slf4j
public class MonitorPointCollector {
    /**
     * 临时监控集合（未完成监控点）
     **/
    public static Map<String, Map<String,MonitorPoint>> tempPointMap = new ConcurrentHashMap<>();
    /**
     * 监控点聚合器（默认5秒）
     **/
    public static List<PointIntegrator> pointIntegrators= Collections.synchronizedList(new ArrayList<>());
    /**
     * 调用方法栈集合
     **/
    public static Map<String, String> methodChainMap=new HashMap<>();

    static {
        pointIntegrators.add(new PointIntegrator());
    }

    /**
     * 获取或者创建监控点
     * @Author: miaozp
     * @Date: 2020/10/31 5:46 下午
     * @param tranceId:
     * @param fullMethodName:
     * @return: com.m.monitor.me.client.point.collector.MonitorPoint
     **/
    public static MonitorPoint createOrGetMonitorPoint(String tranceId, String fullMethodName) {
        Map<String,MonitorPoint> pointMap= createOrGetPointMap(tranceId,fullMethodName);
        MonitorPoint monitorPoint=pointMap.get(tranceId);
        if (monitorPoint==null){
            monitorPoint=new MonitorPoint(fullMethodName, System.currentTimeMillis());
            pointMap.put(tranceId, monitorPoint);
        }
        return monitorPoint;
    }
    /**
     * 获取或者创建监控点
     * @Author: miaozp
     * @Date: 2020/10/31 5:46 下午
     * @param tranceId:
     * @param fullMethodName:
     * @return: com.m.monitor.me.client.point.collector.MonitorPoint
     **/
    public static MonitorPoint createOrGetMonitorPointWithArgs(String tranceId, String fullMethodName,Object[] paramArgs) {
        MonitorPoint monitorPoint=createOrGetMonitorPoint( tranceId,fullMethodName);
        monitorPoint.setParamArgs(paramArgs);
        return monitorPoint;
    }
    /**
     * 获取或者创建一个临时监控点击集合
     * @Author: miaozp
     * @Date: 2020/10/31 5:47 下午
     * @param tranceId:
     * @param fullMethodName:
     * @return: java.util.Map<java.lang.String, com.m.monitor.me.client.point.collector.MonitorPoint>
     **/
    public static Map<String,MonitorPoint> createOrGetPointMap(String tranceId, String fullMethodName) {
        Map<String,MonitorPoint> pointMap= tempPointMap.get(fullMethodName);
        if (pointMap==null) {
            pointMap=new ConcurrentHashMap(50);
            tempPointMap.put(fullMethodName,pointMap);
        }
       return pointMap;
    }
    /**
     * 监控点完成
     * @Author: miaozp
     * @Date: 2020/10/31 5:48 下午
     * @param tranceId:
     * @param fullMethodName:
     * @return: void
     **/
    public static void finished(String tranceId,String fullMethodName){
        MonitorPoint point=createOrGetMonitorPoint(tranceId,fullMethodName);
        //监控点结束
        point.finished();
        //放入聚合器
        createOrGetIntegrator(point).put(point);

        //放入方法连
        methodChainMap.put(point.getFullMethodName(), point.toString());

        //移除收集器
        createOrGetPointMap(tranceId,fullMethodName).remove(tranceId);
    }
    /**
     * 获取或者创建一个监控聚合器
     * @Author: miaozp
     * @Date: 2020/10/31 5:48 下午
     * @param point:
     * @return: com.m.monitor.me.client.point.integrator.PointIntegrator
     **/
    public static PointIntegrator createOrGetIntegrator(MonitorPoint point){
        PointIntegrator integrator=pointIntegrators.get(pointIntegrators.size()-1);
        int len=integrator.getTotal().intValue();
        Long second= Long.parseLong(DateUtil.formatSecond(point.getEndTime()));
        //5秒新建一个聚合器
        if(integrator.getIntegratorMap().size()==5&&integrator.get(second)==null){
            integrator=new PointIntegrator();
            pointIntegrators.add(integrator);
        }
        return integrator;
    }
    /**
     * 从聚合器移除
     * @Author: miaozp
     * @Date: 2020/10/31 5:49 下午
     * @param pointIntegrator:
     * @return: void
     **/
    public static  void remove(PointIntegrator pointIntegrator){
        pointIntegrators.remove(pointIntegrator);
    }
    /**
     * 调用聚合器中信息
     * @Author: miaozp
     * @Date: 2020/10/31 5:50 下午
     * @return: void
     **/
    public static void printPointIntegrator(){
       for(PointIntegrator integrator:pointIntegrators){
           log.info(JSON.toJSONString(integrator));
       }
    }
}

