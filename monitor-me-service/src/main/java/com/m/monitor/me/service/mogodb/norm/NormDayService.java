package com.m.monitor.me.service.mogodb.norm;

import com.m.beyond.view.data.charts.BarchartData;
import com.m.monitor.me.service.transfer.server.record.IntegratorNormRecord;
import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
