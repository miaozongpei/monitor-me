package com.m.monitor.me.client.transfer.schedule;

import com.alibaba.fastjson.JSON;
import com.m.monitor.me.client.point.collector.MonitorPointCollector;
import com.m.monitor.me.client.point.integrator.PointIntegrator;
import com.m.monitor.me.client.transfer.client.MonitorExpressWayClient;
import com.m.monitor.me.client.transfer.file.MonitorWriterManager;
import com.m.monitro.me.common.transfer.IntegratorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class IntegratorTruckRunnable implements Runnable {
    @Value("${monitor.me.application.name:monitor-me}")
    private String monitorApplicationName="monitor-me";

    @Value("${monitor.me.admin.host:localhost}")
    private String host="localhost";

    @Value("${monitor.me.admin.port:8899}")
    private int port=8899;
    @Resource
    private MonitorExpressWayClient monitorExpressWayClient;
    @Override
    public void run() {
        List<PointIntegrator>  integrators= MonitorPointCollector.pointIntegrators;
        while (integrators.size()>1){
            PointIntegrator pointIntegrator=integrators.get(0);
            synchronized (pointIntegrator) {
                if (transfer(pointIntegrator)) {
                    MonitorPointCollector.pointIntegrators.remove(pointIntegrator);
                }
            }
        }
        //MonitorWriterManager.getInstance().flush();
    }
    public boolean transfer(PointIntegrator pointIntegrator){
        String jsonPointIntegrator=JSON.toJSONString(new IntegratorContext(monitorApplicationName,pointIntegrator.getIntegratorMap()));
        if (monitorExpressWayClient.checkAndConnect(host,port)) {
            Boolean isSend = monitorExpressWayClient.send(jsonPointIntegrator);
        }
        //如果发送失败写入本地文件
        //return isSend?true:MonitorWriterManager.getInstance().writer(jsonPointIntegrator);
        return true;
    }
}
