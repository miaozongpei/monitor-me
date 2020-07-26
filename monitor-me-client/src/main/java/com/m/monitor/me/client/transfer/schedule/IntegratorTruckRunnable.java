package com.m.monitor.me.client.transfer.schedule;

import com.alibaba.fastjson.JSON;
import com.m.monitor.me.client.point.collector.MonitorPointCollector;
import com.m.monitor.me.client.point.integrator.PointIntegrator;
import com.m.monitor.me.client.transfer.client.MonitorExpressWayClient;
import com.m.monitor.me.client.transfer.file.MonitorWriterManager;

import java.util.List;

public class IntegratorTruckRunnable implements Runnable {
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
        MonitorWriterManager.getInstance().flush();
    }
    public boolean transfer(PointIntegrator pointIntegrator){
        String jsonPointIntegrator=JSON.toJSONString(pointIntegrator.getIntegratorMap());
        Boolean isSend=MonitorExpressWayClient.getInstance().send(jsonPointIntegrator);
        //如果发送失败写入本地文件
        return isSend?true:MonitorWriterManager.getInstance().writer(jsonPointIntegrator);
    }
}
