package com.m.monitor.me.client.point;

import com.m.monitor.me.client.point.collector.MethodChainCollector;
import com.m.monitor.me.client.point.collector.MonitorPoint;
import com.m.monitor.me.client.point.collector.MonitorPointCollector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class MonitorHandler extends AbstractAspectHandler{
    public static ThreadLocal<String> traceId = new ThreadLocal<>();
    public static ThreadLocal<AtomicInteger> seq = new ThreadLocal<>();

    @Override
    public void doBefore(MonitorContext context) {
        String fullMethodName=this.getFullMethodName(context.getPoint());
        if(StringUtils.isEmpty(traceId.get())) {
            //创建traceId
            traceId.set(fullMethodName + ":" + UUID.randomUUID().toString());
            //创建访问顺序
            seq.set(new AtomicInteger(-1));
            //创建监控点（point）
            MonitorPointCollector.create(traceId.get(), fullMethodName);
        }else {
            String rootMethodName = traceId.get().split(":")[0];
            String chainName=seq.get().incrementAndGet()+":"+fullMethodName;
            MethodChainCollector.checkAndPut(rootMethodName,chainName);
            context.setChainName(chainName);
        }
    }

    @Override
    public void doAfter(MonitorContext context) {
        if (StringUtils.isEmpty(traceId.get())) {
            return;
        }
        String rootMethodName=traceId.get().split(":")[0];
        String fullMethodName=this.getFullMethodName(context.getPoint());
        if (rootMethodName.contains(fullMethodName)) {
            //终止方法调用链
            MethodChainCollector.checkAndPut(rootMethodName,MethodChainCollector.STOP);
            //监控点结束
            MonitorPointCollector.finished(traceId.get());
            //清除traceId
            traceId.remove();

        }else{
            //获取监控点
            MonitorPoint monitorPoint=MonitorPointCollector.pointMap.get(traceId.get());
            //添加执行时间
            Integer integer=Integer.parseInt(context.getChainName().split(":")[0]);
            monitorPoint.put(integer,System.currentTimeMillis()-context.getChainStartTime());
        }
    }

}
