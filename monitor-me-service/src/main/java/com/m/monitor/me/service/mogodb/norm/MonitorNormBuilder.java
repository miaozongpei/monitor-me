package com.m.monitor.me.service.mogodb.norm;


import com.m.monitor.me.service.transfer.server.norm.MethodNorm;
import com.m.monitor.me.service.transfer.server.norm.TimeNorm;
import com.m.monitor.me.service.transfer.server.task.IntegratorRecord;
import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import com.m.monitro.me.common.utils.DateUtil;
import com.m.monitro.me.common.utils.DoubleUtil;
import com.m.monitro.me.common.utils.MonitorTimeUtil;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.*;

@Data
public class MonitorNormBuilder {
    private long currentTime;
    private MonitorTimeUnitEnum timeUnit;
    private int size;

    private long beforeTime;
    public MonitorNormBuilder(long currentTime,MonitorTimeUnitEnum timeUnit,int size) {
        this.currentTime = MonitorTimeUtil.toTime(currentTime,timeUnit);
        this.timeUnit = timeUnit;
        this.size=size;
        this.beforeTime=MonitorTimeUtil.subTime(currentTime,size,timeUnit);
    }

    private Map<Long,Double> buildNormMap(){
        Map<Long,Double> normMap=new TreeMap<>();
        for (int i=0;i<size;i++){
            long targetTime = MonitorTimeUtil.subTime(currentTime,i, timeUnit);
           if (normMap.get(targetTime)==null){
               normMap.put(targetTime,0D);
           }
        }
        return normMap;
    }

    public List<double[]> build(List<IntegratorRecord> records,String targetMethod) {
        Map<Long,Double> normMap=buildNormMap();
        for (IntegratorRecord record : records) {
            for (TimeNorm timeNorm : record.getTs()) {
                long targetTime = MonitorTimeUtil.toTime(timeNorm.getT(), timeUnit);
                Double targetNorm=normMap.get(targetTime);
                if (targetNorm!=null) {
                    if (StringUtils.isEmpty(targetMethod)) {
                        normMap.put(targetTime, targetNorm + timeNorm.getAvg());
                    } else {
                        for (MethodNorm methodNorm : timeNorm.getMs()) {
                            if (methodNorm.getM().equals(targetMethod)) {
                                normMap.put(targetTime, normMap.get(targetTime) + timeNorm.getAvg());
                            }
                        }
                    }
                }
            }
        }

        List<double[]> targetList=new ArrayList<>();
        for (Map.Entry<Long,Double> entry:normMap.entrySet()){
            long time=DateUtil.parse(entry.getKey()+"",DateUtil.FORMAT_YYYYMMDDHHMISS).getTime();
            targetList.add(new double[]{Double.parseDouble(time+""), DoubleUtil.avg(entry.getValue(),timeUnit.unit)});
        }
        return targetList;
    }







}
