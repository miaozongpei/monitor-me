package com.m.monitor.me.service.transfer.server.builder;

import com.m.monitor.me.service.transfer.server.norm.MethodNorm;
import com.m.monitor.me.service.transfer.server.norm.TimeNorm;
import com.m.monitor.me.service.transfer.server.record.IntegratorNormRecord;
import com.m.monitor.me.service.transfer.server.record.MonitorPointRecord;
import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import com.m.monitro.me.common.transfer.IntegratorContext;
import com.m.monitro.me.common.utils.MonitorTimeUtil;
import lombok.Getter;

import java.util.List;
import java.util.Map;
@Getter
public class IntegratorNormBuilder {
    private IntegratorNormRecord secondRecord;
    private IntegratorNormRecord minuteRecord;
    private IntegratorNormRecord hourRecord;
    private IntegratorNormRecord dayRecord;

    private MonitorPointRecord monitorPointRecord;


    private TimeNormBuilder timeNormBuilder=new TimeNormBuilder();

    private IntegratorContext context;
    private String host;
    private String name;

    public IntegratorNormBuilder(IntegratorContext context) {
        this.context = context;
        this.name=context.getName();
        this.host=context.getHost();
        putIntegratorContext();
    }

    public IntegratorNormBuilder build(){
        secondRecord=new IntegratorNormRecord(name,host);
        minuteRecord=new IntegratorNormRecord(name,host);
        hourRecord=new IntegratorNormRecord(name,host);
        dayRecord=new IntegratorNormRecord(name,host);

        monitorPointRecord=new MonitorPointRecord(name,host);

        secondRecord.setTs(timeNormBuilder.buildTimeNorms(timeNormBuilder.getSecondMap()));
        minuteRecord.setTs(timeNormBuilder.buildTimeNorms(timeNormBuilder.getMinuteMap()));
        hourRecord.setTs(timeNormBuilder.buildTimeNorms(timeNormBuilder.getHourMap()));
        dayRecord.setTs(timeNormBuilder.buildTimeNorms(timeNormBuilder.getDayMap()));
        return this;
    }
    private void putIntegratorContext(){
        for(Object key:context.getIts().keySet()){
            Long time=(Long)key;
            timeNormBuilder.putTimeNorm(time,(Map)context.getIts().get(key));
        }
    }
    public void putBeforeIntegratorNormRecord(MonitorTimeUnitEnum unitEnum,IntegratorNormRecord integratorNormRecord){
        for (TimeNorm timeNorm:integratorNormRecord.getTs()){
            long time=timeNorm.getT();
            for (MethodNorm methodNorm:timeNorm.getMs()){
                timeNormBuilder.putTimeNorm(time,methodNorm,unitEnum);
            }
        }
    }
    public IntegratorNormRecord getIntegratorNormRecord(MonitorTimeUnitEnum unitEnum){
        if (unitEnum==MonitorTimeUnitEnum.SECOND) {
            return secondRecord;
        }else if(unitEnum==MonitorTimeUnitEnum.MINUTE) {
            return minuteRecord;
        }else if(unitEnum==MonitorTimeUnitEnum.HOUR) {
            return hourRecord;
        }else if (unitEnum==MonitorTimeUnitEnum.DAY){
            return dayRecord;
        }
        return null;
    }
    public TimeNorm getTimeNormBuilder(Long time,MonitorTimeUnitEnum unitEnum){
        if (unitEnum==MonitorTimeUnitEnum.SECOND) {
            return timeNormBuilder.buildTimeNorm(time,timeNormBuilder.getSecondMap().get(time));
        }else if(unitEnum==MonitorTimeUnitEnum.MINUTE) {
            return timeNormBuilder.buildTimeNorm(time,timeNormBuilder.getMinuteMap().get(time));
        }else if(unitEnum==MonitorTimeUnitEnum.HOUR) {
            return timeNormBuilder.buildTimeNorm(time,timeNormBuilder.getHourMap().get(time));
        }else if (unitEnum==MonitorTimeUnitEnum.DAY){
            return timeNormBuilder.buildTimeNorm(time,timeNormBuilder.getDayMap().get(time));
        }
        return null;
    }

}
