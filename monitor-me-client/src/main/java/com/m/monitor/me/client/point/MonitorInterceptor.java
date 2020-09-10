package com.m.monitor.me.client.point;


import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.annotation.Resource;
@Slf4j
public class MonitorInterceptor implements MethodInterceptor {
    @Resource
    private MonitorHandler monitorHandler;
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("[MonitorMe client] MonitorInterceptor invoke method:{}",invocation.getMethod());
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
