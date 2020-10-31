package com.m.monitor.me.client.transfer.task;

import com.m.monitor.me.client.point.collector.MonitorPoint;
import com.m.monitor.me.client.point.collector.MonitorPointCollector;
import com.m.monitro.me.common.utils.MethodTraceIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
/**
 * 清除监控点临时集合中未完成的监控点
 * @Author: miaozp
 * @Date: 2020/10/31 6:44 下午
 **/
@Component
@Slf4j
public class ClearTempPointMapTask implements Runnable {
    /**
     *清除过期
     **/
    private long clearExpire=5*60*1000;
    @Override
    public void run() {
        try {
            for (String method:MonitorPointCollector.tempPointMap.keySet()) {
                Map<String, MonitorPoint> methodPointMap=MonitorPointCollector.tempPointMap.get(method);
                clearMonitorPointMap(methodPointMap);
            }
        }catch (Exception e){
            e.printStackTrace();
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
