package com.m.monitor.me.client.handler;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.JoinPoint;

@Getter
@Setter
public class MonitorContext {
    private JoinPoint point;

    private String chainName;
    private long chainStartTime;

    public MonitorContext(JoinPoint point) {
        this.point = point;
        this.chainStartTime=System.currentTimeMillis();
    }
}
