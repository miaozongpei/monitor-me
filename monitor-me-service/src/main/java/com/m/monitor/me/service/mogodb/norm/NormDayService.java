package com.m.monitor.me.service.mogodb.norm;

import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import org.springframework.stereotype.Component;

@Component
public class NormDayService extends BaseNormService{

    @Override
    public String getCollectionName() {
        return "day_norm";
    }

    @Override
    public MonitorTimeUnitEnum getMonitorTimeUnitEnum() {
        return MonitorTimeUnitEnum.DAY;
    }


}
