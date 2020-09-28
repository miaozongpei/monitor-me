package com.m.monitor.me.client.point.collector;

import com.m.monitro.me.common.utils.DateUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Slf4j
public class MonitorPoint extends HashMap<Integer,Long> {
    private String fullMethodName;
    private Long startTime=0L;
    private Long endTime=0L;
    private List<MethodChain> chains;
    private MethodChains methodChains;

    public MonitorPoint(String fullMethodName, Long startTime) {
        super();
        this.fullMethodName = fullMethodName;
        this.startTime = startTime;
        this.initChains();
    }
    private void initChains(){
        this.chains=MethodChainCollector.chainMap.get(this.fullMethodName);
        if (this.chains==null){
            this.chains=new ArrayList<MethodChain>();
            MethodChainCollector.chainMap.put(this.fullMethodName,this.chains);
        }
    }

    public void finished(){
        this.endTime=System.currentTimeMillis();

        this.methodChains= new MethodChains(new MethodChain(-1,this.fullMethodName), 0,this.getNorm(),this.getNorm());
        this.methodChains.buildChildren(this);
    }


    public void print(){
        System.out.println(this);

    }

    @Override
    public String toString() {
        StringBuffer str=new StringBuffer();
        str.append(this.methodChains).append("|").append(getNorm()).append("ms|").append(DateUtil.format(startTime,DateUtil.FORMAT_YYYYMMDDHHMISSSSS)).append("\n");
        str.append(this.methodChains.childrenToStr(this.methodChains.getChildren()));
        return str.toString();
    }

    public long getNorm(){
        return endTime-startTime;
    }
}
