package com.m.monitor.me.client.point;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.annotation.Resource;

public class MonitorInterceptor implements MethodInterceptor {
    @Resource
    private MonitorHandler monitorHandler;
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object proceed = null;
        try {
            MonitorContext context=new MonitorContext(invocation.getMethod());
            monitorHandler.doBefore(context);
            proceed = invocation.proceed();
            monitorHandler.doAfter(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proceed;
    }
}