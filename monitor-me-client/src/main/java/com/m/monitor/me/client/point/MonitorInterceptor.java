package com.m.monitor.me.client.point;


import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import javax.annotation.Resource;

/**
 * 监控点拦截器
 *
 * @Author: miaozp
 * @Date: 2020/10/31 6:24 下午
 **/
@Slf4j
public class MonitorInterceptor implements MethodInterceptor {
    @Resource
    private MonitorHandler monitorHandler;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.debug("[MonitorMe client] MonitorInterceptor invoke method:{}", invocation.getMethod());
        Object proceed = null;
        MonitorContext context = new MonitorContext(invocation.getMethod(), invocation.getArguments());
        try {
            monitorHandler.doBeforeLimit(context);
            monitorHandler.doBefore(context);
        } catch (Exception e) {
            log.error("MonitorInterceptor before error:",e);
        }

        proceed = invocation.proceed();

        try {
            monitorHandler.doAfter(context);
        } catch (Exception e) {
            log.error("MonitorInterceptor after error:",e);
        }
        return proceed;
    }
}
