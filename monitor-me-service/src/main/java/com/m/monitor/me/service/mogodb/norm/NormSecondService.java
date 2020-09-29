package com.m.monitor.me.service.mogodb.norm;

import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import org.springframework.stereotype.Component;

@Component
public class NormSecondService extends BaseNormService{
    private String collectionName="norm_second";

    @Override
    public String getCollectionName() {
        return "second_norm";
    }

    @Override
    public MonitorTimeUnitEnum getMonitorTimeUnitEnum() {
        return MonitorTimeUnitEnum.SECOND;
    }
}
