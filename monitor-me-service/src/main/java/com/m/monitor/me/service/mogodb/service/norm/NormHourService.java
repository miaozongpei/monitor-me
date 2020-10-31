package com.m.monitor.me.service.mogodb.service.norm;

import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import org.springframework.stereotype.Component;
/**
 * 根据小时汇总监控指标Service类
 * @Author: miaozp
 * @Date: 2020/10/31 2:29 下午
 * @Param: * @param null:
 * @Return: * @return: null
 **/
@Component
public class NormHourService extends BaseNormService {

    @Override
    public String getCollectionName() {
        return "hour_norm";
    }

    @Override
    public MonitorTimeUnitEnum getMonitorTimeUnitEnum() {
        return MonitorTimeUnitEnum.HOUR;
    }
}
