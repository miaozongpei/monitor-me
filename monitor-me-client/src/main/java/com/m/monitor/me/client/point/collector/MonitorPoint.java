package com.m.monitor.me.client.point.collector;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Slf4j
public class MonitorPoint extends HashMap<Integer,Long> {
    private String fullMethodName;
    private Long startTime=0L;
    private Long endTime=0L;
    private ArrayList<String> chains;

    public MonitorPoint(String fullMethodName, Long startTime) {
        super();
        this.fullMethodName = fullMethodName;
        this.startTime = startTime;
        this.initChains();

    }
    private void initChains(){
        this.chains=MethodChainCollector.chainMap.get(this.fullMethodName);
        if (this.chains==null){
            this.chains=new ArrayList<String>();
            MethodChainCollector.chainMap.put(this.fullMethodName,this.chains);
        }
    }

    public void finished(){
        this.endTime=System.currentTimeMillis();
    }

    public void print(){

        System.out.println(this.toString());

    }

    @Override
    public String toString() {
        long totalTime=endTime-startTime;
        StringBuffer str=new StringBuffer();
        str.append(fullMethodName).append(':').append(totalTime).append("ms").append("\n");
        for(int i=0;i<chains.size()-1;i++){
            Long time=get(i);
            str.append(new DecimalFormat("0.00%").format(time.doubleValue()/totalTime));
            str.append('-').append(chains.get(i)).append(':');
            str.append(time).append("ms");
            str.append("\n");
        }
        return str.toString();
    }

    public long getNorm(){
        return endTime-startTime;
    }
}
