package com.m.monitor.me.service.transfer.server.norm;

import com.m.monitro.me.common.utils.DoubleUtil;

import java.util.ArrayList;
import java.util.List;

public class TimeNorm{
    private long t;
    private double avg;
    private long total;
    private List<MethodNorm> ms=new ArrayList<>();

    public void add(MethodNorm m){
        ms.add(m);
    }
    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }

    public List<MethodNorm> getMs() {
        return ms;
    }
    public MethodNorm getMethodNorm(String methodName){
        for (MethodNorm m:ms){
            if (m.getM().equals(methodName)){
                return m;
            }
        }
        return null;
    }
    public void setMs(List<MethodNorm> ms) {
        this.ms = ms;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void cal(){
        double sumAvg=0D;
        for(MethodNorm norm:ms){
            sumAvg+=norm.getAvg();
            this.total+=norm.getTotal();
        }
        this.avg= DoubleUtil.avg(sumAvg,ms.size());
    }
}