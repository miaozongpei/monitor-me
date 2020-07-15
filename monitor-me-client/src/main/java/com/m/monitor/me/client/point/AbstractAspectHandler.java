package com.m.monitor.me.client.point;

import org.aspectj.lang.JoinPoint;

public abstract class AbstractAspectHandler {
    public Class getClass(JoinPoint joinPoint){
        return joinPoint.getTarget().getClass();
    }
    public String getClassName(JoinPoint joinPoint){
        return getClass(joinPoint).getName();
    }
    public String getMethodName(JoinPoint joinPoint){
        return joinPoint.getSignature().getName();
    }
    public String getFullMethodName(JoinPoint joinPoint){
        return joinPoint.getTarget().getClass().getName()+"."+joinPoint.getSignature().getName();
    }

    public abstract void doBefore(MonitorContext context);
    public abstract void doAfter(MonitorContext context);

}
