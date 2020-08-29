package com.m.monitor.me.service.transfer.server.norm;

import com.m.monitro.me.common.utils.DoubleUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class TimeNorm{
    private long t;
    private double avg;
    private long total;
    private List<MethodNorm> ms=new ArrayList<>();

    public TimeNorm(long t) {
        this.t = t;
    }
    public MethodNorm getMethodNorm(String methodName){
        for (MethodNorm m:ms){
            if (m.getM().equals(methodName)){
                return m;
            }
        }
        return null;
    }
    public void cal(){
        double sumAvg=0D;
        long sumTotal=0L;
        for(MethodNorm norm:ms){
            sumAvg+=norm.getAvg();
            sumTotal+=norm.getTotal();
        }
        this.total=sumTotal;
        this.avg= DoubleUtil.avg(sumAvg,ms.size());
    }
}