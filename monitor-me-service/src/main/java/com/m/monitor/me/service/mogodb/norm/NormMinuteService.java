package com.m.monitor.me.service.mogodb.norm;

import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import org.springframework.stereotype.Component;

@Component
public class NormMinuteService extends BaseNormService{

    @Override
    public String getCollectionName() {
        return "minute_norm";
    }

    @Override
    public MonitorTimeUnitEnum getMonitorTimeUnitEnum() {
        return MonitorTimeUnitEnum.MINUTE;
    }
}
