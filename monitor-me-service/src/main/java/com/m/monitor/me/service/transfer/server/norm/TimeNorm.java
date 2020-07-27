package com.m.monitor.me.service.transfer.server.norm;

import java.util.ArrayList;
import java.util.List;

public class TimeNorm{
    private long t;
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

    public void setMs(List<MethodNorm> ms) {
        this.ms = ms;
    }
}