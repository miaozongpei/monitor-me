package com.m.monitor.me.client.point.collector;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.m.monitro.me.common.utils.DateUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * 监控点
 * @Author: miaozp
 * @Date: 2020/10/31 5:39 下午
 **/
@Getter
@Slf4j
public class MonitorPoint extends HashMap<Integer,Long> {
    private String fullMethodName;
    private Long startTime=0L;
    private Long endTime=0L;
    private List<MethodChain> chains=new ArrayList<MethodChain>();
    private MethodChains methodChains;
    private Object[] paramArgs;

    public MonitorPoint(String fullMethodName, Long startTime) {
        super();
        this.fullMethodName = fullMethodName;
        this.startTime = startTime;
    }
    public MonitorPoint(String fullMethodName, Long startTime,Object[] paramArgs) {
        super();
        this.fullMethodName = fullMethodName;
        this.startTime = startTime;
        this.paramArgs=paramArgs;
    }
    /**
     * 监控点完成
     * @Author: miaozp
     * @Date: 2020/10/31 5:40 下午
     * @return: void
     **/
    public void finished(){
        this.endTime=System.currentTimeMillis();
        //构建层级关系
        this.methodChains= new MethodChains(new MethodChain(-1,this.fullMethodName), 0,this.getNorm(),this.getNorm());
        this.methodChains.buildChildren(this);
    }

    public void print(){
        log.info(toString());
    }

    @Override
    public String toString() {
        StringBuffer str=new StringBuffer();
        str.append(this.methodChains).append("|").append(getNorm()).append("ms|").append(DateUtil.format(startTime,DateUtil.FORMAT_YYYYMMDDHHMISSSSS)).append("\n");
        str.append(this.methodChains.childrenToStr(this.methodChains.getChildren()));
        if (this.paramArgs!=null) {
            str.append("\n");
            str.append(JSON.toJSONString(this.paramArgs));
        }
        return str.toString();
    }

    public long getNorm(){
        return endTime-startTime;
    }

    public void setParamArgs(Object[] paramArgs) {
        this.paramArgs = paramArgs;
    }
}
