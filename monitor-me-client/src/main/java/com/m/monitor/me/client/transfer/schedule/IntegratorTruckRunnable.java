package com.m.monitor.me.client.transfer.schedule;

import com.alibaba.fastjson.JSON;
import com.m.monitor.me.client.point.collector.MonitorPointCollector;
import com.m.monitor.me.client.point.integrator.PointIntegrator;
import com.m.monitro.me.common.enums.MonitorTransferTypeEnum;
import com.m.monitro.me.common.transfer.IntegratorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class IntegratorTruckRunnable extends AbstractTruckRunnable implements Runnable {
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
