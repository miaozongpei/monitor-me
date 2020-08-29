package com.m.monitor.me.service.transfer.server.builder;

import com.m.monitor.me.service.transfer.server.record.IntegratorNormRecord;
import com.m.monitro.me.common.transfer.IntegratorContext;
import lombok.Getter;

import java.util.Map;
@Getter
public class IntegratorNormBuilder {
    private IntegratorNormRecord secondRecord;
    private IntegratorNormRecord minuteRecord;
    public IntegratorNormBuilder build(IntegratorContext context){
        secondRecord=new IntegratorNormRecord(context.getName(),context.getHost());
        minuteRecord=new IntegratorNormRecord(context.getName(),context.getHost());
        TimeNormBuilder timeNormBuilder=new TimeNormBuilder();
        for(Object key:context.getIts().keySet()){
            Long time=(Long)key;
            timeNormBuilder.buildTimeNorm(time,(Map)context.getIts().get(key));
        }
        secondRecord.setTs(timeNormBuilder.buildSecond());
        minuteRecord.setTs(timeNormBuilder.buildMinute());
        return this;
    }
}
