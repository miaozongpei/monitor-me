package com.m.monitor.me.client.transfer.task;

import com.m.monitro.me.common.enums.MonitorTransferTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
/**
 * 发送心跳任务
 * @Author: miaozp
 * @Date: 2020/10/31 6:46 下午
 **/
@Component
@Slf4j
public class HeartBeatTruckTask extends AbstractTruckTask{
    @Value("${monitor.me.application.name:monitor-me}")
    private String monitorApplicationName="monitor-me";
    @Override
    public MonitorTransferTypeEnum transferType() {
        return MonitorTransferTypeEnum.HEART_BEAT;
    }

    @Override
    public List<Object> transferContents() {
        return Arrays.asList(monitorApplicationName);
    }
}
