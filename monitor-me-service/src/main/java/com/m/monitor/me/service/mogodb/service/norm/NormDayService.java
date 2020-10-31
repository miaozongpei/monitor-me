package com.m.monitor.me.service.mogodb.service.norm;

import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import org.springframework.stereotype.Component;
/**
 * 根据天数汇总监控指标Service类
 * @Author: miaozp
 * @Date: 2020/10/31 2:28 下午
 * @Param: * @param null:
 * @Return: * @return: null
 **/
@Component
public class NormDayService extends BaseNormService {

    @Override
    public String getCollectionName() {
        return "day_norm";
    }

    @Override
    public MonitorTimeUnitEnum getMonitorTimeUnitEnum() {
        return MonitorTimeUnitEnum.DAY;
    }


}
