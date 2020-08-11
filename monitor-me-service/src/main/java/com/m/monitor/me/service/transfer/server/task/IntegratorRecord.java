package com.m.monitor.me.service.transfer.server.task;

import com.m.monitor.me.service.transfer.server.norm.MethodNorm;
import com.m.monitor.me.service.transfer.server.norm.TimeNorm;
import com.m.monitro.me.common.transfer.IntegratorContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IntegratorRecord {
    private String name;
    private String host;
    private List<TimeNorm> ts=new ArrayList<>();

    public IntegratorRecord(String name, String host) {
        this.name = name;
        this.host = host;
    }
    public IntegratorRecord build(IntegratorContext context){
        this.name = context.getName();
        this.host = context.getHost();
        for(Object key:context.getIts().keySet()){
            TimeNorm timeNorm=new TimeNorm();
            timeNorm.setT((Long)key);
            buildMethodNorm(timeNorm,(Map) context.getIts().get(key));
            timeNorm.cal();
            this.ts.add(timeNorm);
        }
        return this;
    }

    private void buildMethodNorm(TimeNorm timeNorm,Map map){
        for(Object key:map.keySet()){
            MethodNorm methodNorm=new MethodNorm();
            methodNorm.setM((String)key);
            Map methodValue= (Map) map.get(key);
            methodNorm.setMax((Integer)methodValue.get("maxNorm"));
            methodNorm.setMin((Integer)methodValue.get("minNorm"));
            methodNorm.setSum((Integer)methodValue.get("sumNorm"));
            methodNorm.setTotal((Integer)methodValue.get("total"));
            methodNorm.setAvg();
            timeNorm.add(methodNorm);
        }
    }
    public IntegratorRecord() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<TimeNorm> getTs() {
        return ts;
    }

    public void setTs(List<TimeNorm> ts) {
        this.ts = ts;
    }
}
