package com.m.monitor.me.client.point;

import com.m.monitor.me.client.point.collector.MethodChain;
import com.m.monitor.me.client.point.collector.MonitorPoint;
import com.m.monitor.me.client.point.collector.MonitorPointCollector;
import com.m.monitor.me.client.point.limit.MonitorLimitException;
import com.m.monitor.me.client.point.limit.PointLimitConfig;
import com.m.monitro.me.common.limit.PointLimit;
import com.m.monitro.me.common.utils.MethodTraceIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 监控切面处理类
 * @Author: miaozp
 * @Date: 2020/10/31 6:51 下午
 **/
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
            MonitorPointCollector.createOrGetMonitorPoint(traceId.get(), fullMethodName);
        }else {
            MethodChain methodChain=new MethodChain(seq.get().incrementAndGet(),fullMethodName);
            //入栈
            methodChainStack.get().push(methodChain);
            String rootMethodName = MethodTraceIdUtil.splitMethodName(traceId.get());

            MonitorPoint monitorPoint=MonitorPointCollector.createOrGetMonitorPoint(traceId.get(),rootMethodName);
            monitorPoint.getChains().add(methodChain);
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
            //调用结束后限制
            doFinishLimit(context);
            //监控点结束
            MonitorPointCollector.finished(traceId.get(),rootMethodName);
            //清除traceId
            traceId.remove();
            seq.remove();
            methodChainStack.remove();



        }else{
            //获取监控点
            MonitorPoint monitorPoint=MonitorPointCollector.createOrGetMonitorPoint(traceId.get(),rootMethodName);
            if(!methodChainStack.get().isEmpty()) {
                //出栈
                MethodChain chain = methodChainStack.get().pop();
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

    @Override
    public void doBeforeLimit(MonitorContext context) throws MonitorLimitException {
        String fullMethodName=getFullMethodName(context);
        PointLimit pointLimit =PointLimitConfig.get(fullMethodName);
        if (pointLimit ==null){
            return;
        }
        Map<String, MonitorPoint> tempPoints = MonitorPointCollector.tempPointMap.get(fullMethodName);
        if (pointLimit.getBreakFlag()>0) {
            throw MonitorLimitException.ErrorEnum.POINT_BROKEN.format();
        }
        if ((tempPoints!=null&&tempPoints.size()> pointLimit.getWaitingThreadMax())) {
            throw MonitorLimitException.ErrorEnum.POINT_OVER_WAITING_THREADS.format(pointLimit.getWaitingThreadMax());
        }
        if (pointLimit.getCurrentTps()!=null&& pointLimit.getCurrentTps().decrementAndGet() > pointLimit.getTpsMax()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            throw MonitorLimitException.ErrorEnum.POINT_OVER_WAITING_THREADS.format(pointLimit.getTpsMax());
        }
    }

    private void doFinishLimit(MonitorContext context){
        try {
            String fullMethodName = getFullMethodName(context);
            PointLimit pointLimit = PointLimitConfig.get(fullMethodName);
            if (pointLimit == null) {
                return;
            }
            if (pointLimit.getSleepMillis() > 0) {
                Thread.sleep(pointLimit.getSleepMillis());
            }
        }catch (Exception e){
            log.error("doAfterLimit error:",e);
        }
    }
}
