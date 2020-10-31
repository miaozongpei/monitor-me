package com.m.monitor.me.client.point;

import com.m.monitor.me.client.point.limit.MonitorLimitException;

/**
 * 抽象监控切面处理类
 * @Author: miaozp
 * @Date: 2020/10/31 6:21 下午
 **/
public abstract class AbstractAspectHandler {
    public String getFullMethodName(MonitorContext context){
        return context.getMethod().getDeclaringClass().getName()+"."+context.getMethod().getName();
    }
    /**
     * 调用之前
     * @Author: miaozp
     * @Date: 2020/10/31 6:21 下午
     * @param context:
     * @return: void
     **/
    public abstract void doBefore(MonitorContext context);
    /**
     * 调用之后
     * @Author: miaozp
     * @Date: 2020/10/31 6:22 下午
     * @param context:
     * @return: void
     **/
    public abstract void doAfter(MonitorContext context);
    /**
     * 调用之前限流
     * @Author: miaozp
     * @Date: 2020/10/31 6:22 下午
     * @param context:
     * @return: void
     * @exception:MonitorLimitException
     **/
    public abstract void doBeforeLimit(MonitorContext context)throws MonitorLimitException;


}
