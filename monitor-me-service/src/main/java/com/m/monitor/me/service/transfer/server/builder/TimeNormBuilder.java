package com.m.monitor.me.service.transfer.server.builder;

import com.m.monitor.me.service.transfer.server.norm.MethodNorm;
import com.m.monitor.me.service.transfer.server.norm.TimeNorm;
import com.m.monitor.me.service.transfer.server.record.MonitorMethod;
import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import com.m.monitro.me.common.utils.MonitorTimeUtil;

import java.util.*;

public class TimeNormBuilder {
    private Map<Long,MethodNormBuilder> secondMap=new HashMap<>();
    private Map<Long,MethodNormBuilder>  minuteMap=new HashMap<>();
    public void buildTimeNorm(long time,Map normMap){
        MethodNormBuilder secondMethodNormBuilder=getOrCreate(secondMap,time);
        MethodNormBuilder minuteMethodNormBuilder=getOrCreate(minuteMap, MonitorTimeUtil.toTime(time, MonitorTimeUnitEnum.MINUTE));
        for(Object key:normMap.keySet()){
            MethodNorm methodNorm=new MethodNorm();
            methodNorm.setM((String)key);
            Map methodValue= (Map) normMap.get(key);
            methodNorm.setMax((Integer)methodValue.get("maxNorm"));
            methodNorm.setMin((Integer)methodValue.get("minNorm"));
            methodNorm.setSum((Integer)methodValue.get("sumNorm"));
            methodNorm.setTotal((Integer)methodValue.get("total"));

            secondMethodNormBuilder.put(methodNorm);
            minuteMethodNormBuilder.put(methodNorm);
        }
    }
    private MethodNormBuilder getOrCreate(Map<Long,MethodNormBuilder> map,Long t){
        MethodNormBuilder methodNormBuilder=map.get(t);
        if (methodNormBuilder==null){
            methodNormBuilder=new MethodNormBuilder();
            map.put(t,methodNormBuilder);
        }
        return methodNormBuilder;
    }

    public List<TimeNorm> buildSecond(){
        List<TimeNorm> timeNorms=new ArrayList<>();
        for (Map.Entry<Long,MethodNormBuilder> entry:this.secondMap.entrySet()){
            Long time=entry.getKey();
            MethodNormBuilder methodNormBuilder=entry.getValue();
            TimeNorm timeNorm=new TimeNorm(time);
            timeNorm.getMs().addAll(methodNormBuilder.getSecondMap().values());
            timeNorm.cal();//计算平均
            timeNorms.add(timeNorm);
        }
        return timeNorms;
    }
    public List<TimeNorm> buildMinute(){
        List<TimeNorm> timeNorms=new ArrayList<>();
        for (Map.Entry<Long,MethodNormBuilder> entry:this.minuteMap.entrySet()){
            Long time=entry.getKey();
            MethodNormBuilder methodNormBuilder=entry.getValue();
            TimeNorm timeNorm=new TimeNorm(time);
            timeNorm.getMs().addAll(methodNormBuilder.getMinuteMap().values());
            timeNorm.cal();//计算平均
            timeNorms.add(timeNorm);
        }
        return timeNorms;
    }
    public Set<MonitorMethod> buildMonitorMethod(){
        Map<String, MethodNorm> all = new HashMap<>();
        for (Map.Entry<Long,MethodNormBuilder> entry:this.minuteMap.entrySet()){
            MethodNormBuilder methodNormBuilder=entry.getValue();
            all.putAll(methodNormBuilder.getMinuteMap());
        }
        Set<MonitorMethod> methods=new HashSet<>();
        for (Map.Entry<String,MethodNorm> entry:all.entrySet()){
            MonitorMethod method=new MonitorMethod(entry.getKey());
            methods.add(method);
        }
        return methods;
    }

}
