package com.m.monitor.me.client.transfer.task;

import com.m.monitor.me.client.point.collector.MonitorPointCollector;
import com.m.monitor.me.client.point.integrator.PointIntegrator;
import com.m.monitro.me.common.enums.MonitorTransferTypeEnum;
import com.m.monitro.me.common.transfer.IntegratorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
/**
 * 传输聚合监控点任务
 * @Author: miaozp
 * @Date: 2020/10/31 6:46 下午
 **/
@Component
@Slf4j
public class IntegratorTruckTask extends AbstractTruckTask {
    @Value("${monitor.me.application.name:monitor-me}")
    private String monitorApplicationName="monitor-me";
    @Override
    public MonitorTransferTypeEnum transferType() {
        return MonitorTransferTypeEnum.POINT_INTEGRATOR;
    }
    @Override
    public List<Object> transferContents() {
        List<PointIntegrator>  integrators= MonitorPointCollector.pointIntegrators;
        List<Object> contents=new ArrayList<>();
        while (integrators.size()>1){
            PointIntegrator pointIntegrator=integrators.get(0);
            synchronized (pointIntegrator) {
                IntegratorContext integratorContext= new IntegratorContext(monitorApplicationName,pointIntegrator.getIntegratorMap(),pointIntegrator.buildMethodChainsMap());
                if (contents.add(integratorContext)) {
                    MonitorPointCollector.pointIntegrators.remove(pointIntegrator);
                }
            }
        }
        return contents;
    }
}
