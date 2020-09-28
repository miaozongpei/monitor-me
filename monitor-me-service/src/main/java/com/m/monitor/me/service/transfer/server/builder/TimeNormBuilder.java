package com.m.monitor.me.service.transfer.server.builder;

import com.m.monitor.me.service.transfer.server.norm.MethodNorm;
import com.m.monitor.me.service.transfer.server.norm.TimeNorm;
import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import com.m.monitro.me.common.utils.MonitorTimeUtil;
import lombok.Getter;

import java.util.*;
@Getter
public class TimeNormBuilder {
    private Map<Long,MethodNormBuilder> secondMap=new HashMap<>();
    private Map<Long,MethodNormBuilder>  minuteMap=new HashMap<>();
    private Map<Long,MethodNormBuilder>  hourMap=new HashMap<>();
    private Map<Long,MethodNormBuilder>  dayMap=new HashMap<>();

    public void putTimeNorm(long time,Map normMap){
        MethodNormBuilder secondMethodNormBuilder=getOrCreate(secondMap,time);
        MethodNormBuilder minuteMethodNormBuilder=getOrCreate(minuteMap, MonitorTimeUtil.toTime(time, MonitorTimeUnitEnum.MINUTE));
        MethodNormBuilder hourMethodNormBuilder=getOrCreate(hourMap, MonitorTimeUtil.toTime(time, MonitorTimeUnitEnum.HOUR));
        MethodNormBuilder dayMethodNormBuilder=getOrCreate(dayMap, MonitorTimeUtil.toTime(time, MonitorTimeUnitEnum.DAY));
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
            hourMethodNormBuilder.put(methodNorm);
            dayMethodNormBuilder.put(methodNorm);
        }
    }
    public void putTimeNorm(long time,MethodNorm methodNorm,MonitorTimeUnitEnum unitEnum){
        if (unitEnum==MonitorTimeUnitEnum.SECOND) {
            MethodNormBuilder secondMethodNormBuilder=getOrCreate(secondMap,time);
            secondMethodNormBuilder.put(methodNorm);
        }else if(unitEnum==MonitorTimeUnitEnum.MINUTE) {
            MethodNormBuilder minuteMethodNormBuilder=getOrCreate(minuteMap, MonitorTimeUtil.toTime(time, MonitorTimeUnitEnum.MINUTE));
            minuteMethodNormBuilder.put(methodNorm);
        }else if(unitEnum==MonitorTimeUnitEnum.HOUR) {
            MethodNormBuilder hourMethodNormBuilder=getOrCreate(hourMap, MonitorTimeUtil.toTime(time, MonitorTimeUnitEnum.HOUR));
            hourMethodNormBuilder.put(methodNorm);
        }else if (unitEnum==MonitorTimeUnitEnum.DAY){
            MethodNormBuilder dayMethodNormBuilder=getOrCreate(dayMap, MonitorTimeUtil.toTime(time, MonitorTimeUnitEnum.DAY));
            dayMethodNormBuilder.put(methodNorm);
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
    public List<TimeNorm> buildTimeNorms(Map<Long,MethodNormBuilder> map){
        List<TimeNorm> timeNorms=new ArrayList<>();
        for (Map.Entry<Long,MethodNormBuilder> entry:map.entrySet()){
            Long time=entry.getKey();
            MethodNormBuilder methodNormBuilder=entry.getValue();
            timeNorms.add(buildTimeNorm(time,methodNormBuilder));
        }
        return timeNorms;
    }
    public TimeNorm buildTimeNorm(Long time,MethodNormBuilder methodNormBuilder){
        TimeNorm timeNorm=new TimeNorm(time);
        timeNorm.getMs().addAll(methodNormBuilder.getMethodNormMap().values());
        timeNorm.cal();//计算平均
        return timeNorm;
    }
}
