package com.m.monitor.me.client.handler;

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

    public abstract void doBefore(JoinPoint point);
    public abstract void doAfter(JoinPoint point);

}
