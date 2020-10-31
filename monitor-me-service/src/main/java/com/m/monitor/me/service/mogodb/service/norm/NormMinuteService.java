package com.m.monitor.me.service.mogodb.service.norm;

import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import org.springframework.stereotype.Component;
/**
 * 根据分钟汇总监控指标Service类
 * @Author: miaozp
 * @Date: 2020/10/31 2:29 下午
 * @Param: * @param null:
 * @Return: * @return: null
 **/
@Component
public class NormMinuteService extends BaseNormService {

    @Override
    public String getCollectionName() {
        return "minute_norm";
    }

    @Override
    public MonitorTimeUnitEnum getMonitorTimeUnitEnum() {
        return MonitorTimeUnitEnum.MINUTE;
    }
}
