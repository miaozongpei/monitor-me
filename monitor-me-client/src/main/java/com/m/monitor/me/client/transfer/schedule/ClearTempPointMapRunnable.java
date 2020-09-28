package com.m.monitor.me.client.transfer.schedule;

import com.m.monitor.me.client.point.collector.MonitorPoint;
import com.m.monitor.me.client.point.collector.MonitorPointCollector;
import com.m.monitro.me.common.utils.MethodTraceIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Component
@Slf4j
public class ClearTempPointMapRunnable implements Runnable {
    private long clearExpire=30*60*1000;//30分钟
    @Override
    public void run() {
        for (String method:MonitorPointCollector.tempPointMap.keySet()) {
            Map<String, MonitorPoint> methodPointMap=MonitorPointCollector.tempPointMap.get(method);
            clearMonitorPointMap(methodPointMap);
        }
    }
    public void clearMonitorPointMap(Map<String,MonitorPoint> monitorPointMap){
        if (CollectionUtils.isEmpty(monitorPointMap)) {
            return;
        }
        for (Map.Entry<String, MonitorPoint> point : monitorPointMap.entrySet()) {
            String traceId = point.getKey();
            if (System.currentTimeMillis() - MethodTraceIdUtil.splitTime(traceId) > clearExpire) {
                monitorPointMap.remove(traceId);
            }
        }
    }
}
