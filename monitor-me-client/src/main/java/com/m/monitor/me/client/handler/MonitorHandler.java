package com.m.monitor.me.client.handler;

import com.m.monitor.me.client.handler.point.MethodChainContext;
import com.m.monitor.me.client.handler.point.MonitorPoint;
import com.m.monitor.me.client.handler.point.MonitorPointContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Component
@Slf4j
public class MonitorHandler extends AbstractAspectHandler{
    public static ThreadLocal<String> traceId = new ThreadLocal<>();
    @Override
    public void doBefore(JoinPoint point) {
        String fullMethodName=this.getFullMethodName(point);
        if(StringUtils.isEmpty(traceId.get())) {
            //创建traceId
            traceId.set(fullMethodName+":"+UUID.randomUUID().toString());
            //创建监控点（point）
            MonitorPointContext.create(traceId.get(),fullMethodName);
        }
        String rootMethodName=traceId.get().split(":")[0];
        MethodChainContext.put(rootMethodName,fullMethodName);
    }

    @Override
    public void doAfter(JoinPoint point) {
        if (StringUtils.isEmpty(traceId.get())) {
            return;
        }
        String rootMethodName=traceId.get().split(":")[0];
        String fullMethodName=this.getFullMethodName(point);

        MonitorPoint monitorPoint=MonitorPointContext.pointMap.get(traceId.get());
        monitorPoint.add(System.currentTimeMillis());
        if (rootMethodName.contains(fullMethodName)) {
            //监控点结束
            MonitorPointContext.pointMap.get(traceId.get()).finished();
            //清除traceId
            traceId.remove();
        }
    }

}
