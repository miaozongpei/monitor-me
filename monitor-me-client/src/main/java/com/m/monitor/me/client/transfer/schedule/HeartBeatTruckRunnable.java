package com.m.monitor.me.client.transfer.schedule;

import com.alibaba.fastjson.JSON;
import com.m.monitor.me.client.point.collector.MonitorPoint;
import com.m.monitor.me.client.point.collector.MonitorPointCollector;
import com.m.monitor.me.client.point.integrator.PointIntegrator;
import com.m.monitor.me.client.transfer.client.MonitorExpressWayClient;
import com.m.monitro.me.common.enums.MonitorTransferTypeEnum;
import com.m.monitro.me.common.transfer.IntegratorContext;
import com.m.monitro.me.common.utils.MethodTraceIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class HeartBeatTruckRunnable extends AbstractTruckRunnable{
    @Override
    public MonitorTransferTypeEnum transferType() {
        return MonitorTransferTypeEnum.HEART_BEAT;
    }

    @Override
    public List<Object> transferContents() {
        return Arrays.asList("ok");
    }
}
