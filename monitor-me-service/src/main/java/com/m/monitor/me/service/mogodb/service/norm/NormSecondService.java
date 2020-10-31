package com.m.monitor.me.service.mogodb.service.norm;

import com.m.monitro.me.common.enums.MonitorTimeUnitEnum;
import org.springframework.stereotype.Component;
/**
 * 根据秒数汇总监控指标Service类（5秒一组）
 * @Author: miaozp
 * @Date: 2020/10/31 2:30 下午
 * @Param: * @param null:
 * @Return: * @return: null
 **/
@Component
public class NormSecondService extends BaseNormService {
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
