package com.m.monitor.me.client.point;

public abstract class AbstractAspectHandler {
    public String getFullMethodName(MonitorContext context){
        return context.getMethod().getDeclaringClass().getName()+"."+context.getMethod().getName();
    }

    public abstract void doBefore(MonitorContext context);
    public abstract void doAfter(MonitorContext context);
    public abstract void doBeforeLimit(MonitorContext context)throws Exception ;


}
