package com.m.monitor.me.client.point;

import com.m.monitor.me.client.point.collector.MethodChain;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.JoinPoint;

import java.lang.reflect.Method;
/**
 * 监控调用上下文
 * @Author: miaozp
 * @Date: 2020/10/31 6:23 下午
 **/
@Getter
@Setter
public class MonitorContext {
    private Method method;
    private Object[] paramArgs;

    private MethodChain methodChain;
    private long chainStartTime;

    public MonitorContext(Method method,Object[] paramArgs) {
        this.method = method;
        this.chainStartTime=System.currentTimeMillis();
    }
}
