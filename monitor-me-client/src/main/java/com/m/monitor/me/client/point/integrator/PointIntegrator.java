package com.m.monitor.me.client.point.integrator;

import com.m.monitor.me.client.point.collector.MonitorPoint;
import com.m.monitor.me.client.point.collector.MonitorPointCollector;
import com.m.monitor.me.client.point.limit.PointLimitConfig;
import com.m.monitro.me.common.limit.PointLimit;
import com.m.monitro.me.common.utils.DateUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
/**
 * 监控聚合器
 * @Author: miaozp
 * @Date: 2020/10/31 5:52 下午
 **/
public class PointIntegrator {
    /**
     * 聚合器集合
     **/
    private Map<Long, Map<String,PerformanceNorm>> integratorMap=new ConcurrentHashMap<>();
    /**
     * 聚合器中监控点总数
     **/
    private AtomicLong total= new AtomicLong(0);

    public void put(MonitorPoint point){
        Long second= Long.parseLong(DateUtil.formatSecond(point.getEndTime()));
        PerformanceNorm performanceNorm=getAndNew(second,point.getFullMethodName());
        performanceNorm.add(point);
        //放入当前指标到限制配置中
        PointLimit pointLimit=PointLimitConfig.get(point.getFullMethodName());
        if (pointLimit!=null) {
            pointLimit.getCurrentTps().set(performanceNorm.getTotal().intValue());
        }
        total.incrementAndGet();
    }
    public Map<String,PerformanceNorm>  get(Long second){
        Map<String,PerformanceNorm> performanceNormMap=integratorMap.get(second);
        return performanceNormMap;
    }
    public Map<String,PerformanceNorm>  getAndNew(Long second){
        Map<String,PerformanceNorm> performanceNormMap=integratorMap.get(second);
        if(performanceNormMap==null){
            performanceNormMap=new LinkedHashMap<>();
            integratorMap.put(second,performanceNormMap);
        }
        return performanceNormMap;
    }
    public PerformanceNorm getAndNew(Long second,String name){
        Map<String,PerformanceNorm> performanceNormMap=getAndNew(second);
        PerformanceNorm performanceNorm=performanceNormMap.get(name);
        if(performanceNorm==null){
            performanceNorm=new PerformanceNorm();
            performanceNormMap.put(name,performanceNorm);
        }
        return performanceNorm;
    }
    public PerformanceNorm getCurrentPerformanceNorm(String method){
        if (!CollectionUtils.isEmpty(integratorMap)) {
            Map<String, PerformanceNorm> performanceNormMap = integratorMap.get(0);
            return performanceNormMap.get(method);
        }

        return null;
    }
    public Map<Long, Map<String, PerformanceNorm>> getIntegratorMap() {
        return integratorMap;
    }

    public AtomicLong getTotal() {
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){ return true;}
        if (o == null || getClass() != o.getClass()){ return false;}
        PointIntegrator that = (PointIntegrator) o;
        return this.hashCode()==that.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(integratorMap, total);
    }

    public Map<String,String> buildMethodChainsMap(){
        Map<String,String> methodChainMap=new HashMap<>(50);
        for (Map<String,PerformanceNorm> map:integratorMap.values()){
            for (String method:map.keySet()){
                String chain=MonitorPointCollector.methodChainMap.get(method);
                if (!StringUtils.isEmpty(chain)){
                    methodChainMap.put(method,chain);
                }
            }
        }
        return methodChainMap;
    }
    /**
     * 格式化输出
     * @Author: miaozp
     * @Date: 2020/10/31 5:54 下午
     * @return: java.lang.String
     **/
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
