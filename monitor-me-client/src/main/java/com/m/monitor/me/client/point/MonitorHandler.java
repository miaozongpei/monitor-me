package com.m.monitor.me.client.point;

import com.m.monitor.me.client.point.collector.MethodChain;
import com.m.monitor.me.client.point.collector.MethodChainCollector;
import com.m.monitor.me.client.point.collector.MonitorPoint;
import com.m.monitor.me.client.point.collector.MonitorPointCollector;
import com.m.monitro.me.common.utils.MethodTraceIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Stack;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class MonitorHandler extends AbstractAspectHandler{
    public static ThreadLocal<String> traceId = new ThreadLocal<>();
    public static ThreadLocal<AtomicInteger> seq = new ThreadLocal<>();
    public static ThreadLocal<Stack<MethodChain>>  methodChainStack= new ThreadLocal<>();

    @Override
    public void doBefore(MonitorContext context) {
        String fullMethodName=getFullMethodName(context);
        if(StringUtils.isEmpty(traceId.get())) {
            //创建traceId
            traceId.set(MethodTraceIdUtil.create(fullMethodName));
            //创建访问顺序
            seq.set(new AtomicInteger(-1));
            methodChainStack.set(new Stack<MethodChain>());
            //创建监控点（point）
            MonitorPointCollector.create(traceId.get(), fullMethodName);
        }else {
            String rootMethodName = MethodTraceIdUtil.splitMethodName(traceId.get());
            //String chainName=seq.get().incrementAndGet()+":"+fullMethodName;
            MethodChain methodChain=new MethodChain(seq.get().incrementAndGet(),fullMethodName);
            methodChainStack.get().push(methodChain);//入栈
            MethodChainCollector.checkAndPut(rootMethodName,methodChain);
            context.setMethodChain(methodChain);
        }
    }

    @Override
    public void doAfter(MonitorContext context) {
        if (StringUtils.isEmpty(traceId.get())) {
            return;
        }
        String rootMethodName=MethodTraceIdUtil.splitMethodName(traceId.get());
        String fullMethodName=getFullMethodName(context);
        if (rootMethodName.contains(fullMethodName)) {
            //终止方法调用链
            MethodChainCollector.checkAndPut(rootMethodName,null);
            //监控点结束
            MonitorPointCollector.finished(traceId.get());
            //清除traceId
            traceId.remove();

        }else{
            //获取监控点
            MonitorPoint monitorPoint=MonitorPointCollector.pointMap.get(traceId.get());
            if(!methodChainStack.get().isEmpty()) {
                MethodChain chain = methodChainStack.get().pop();//出栈
            }
            if(!methodChainStack.get().isEmpty()) {
                MethodChain parent = methodChainStack.get().peek();
                if (parent != null) {
                    context.getMethodChain().setParentIndex(parent.getInvokeSeq());
                }
            }
            //添加执行时间
            monitorPoint.put(context.getMethodChain().getInvokeSeq(),System.currentTimeMillis()-context.getChainStartTime());

        }
    }

}
