package com.m.monitor.me.client.point;

import com.m.monitor.me.client.point.collector.MethodChain;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.JoinPoint;

import java.lang.reflect.Method;

@Getter
@Setter
public class MonitorContext {
    private Method method;
    private Object[] args;

    private MethodChain methodChain;
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
