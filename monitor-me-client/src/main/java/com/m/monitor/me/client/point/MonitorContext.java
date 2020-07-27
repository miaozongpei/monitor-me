package com.m.monitor.me.client.point;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.JoinPoint;

import java.lang.reflect.Method;

@Getter
@Setter
public class MonitorContext {
    private Method method;
    private Object[] args;

    private String chainName;
    private long chainStartTime;

    public MonitorContext(Method method) {
        this.method = method;
        this.chainStartTime=System.currentTimeMillis();
    }

    public MonitorContext(Method method,Object[] args) {
        this.method = method;
        this.args = args;
        this.chainStartTime=System.currentTimeMillis();
    }
}
