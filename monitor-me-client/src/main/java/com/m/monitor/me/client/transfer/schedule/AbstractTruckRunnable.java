package com.m.monitor.me.client.transfer.schedule;

import com.alibaba.fastjson.JSON;
import com.m.monitor.me.client.transfer.client.MonitorExpressWayClient;
import com.m.monitro.me.common.enums.MonitorTransferTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
public abstract class AbstractTruckRunnable implements Runnable {
    @Value("${monitor.me.admin.host:localhost}")
    private String host="localhost";

    @Value("${monitor.me.admin.port:8899}")
    private int port=8899;
    @Resource
    private MonitorExpressWayClient monitorExpressWayClient;
    @Override
    public void run() {
        try {
            for (Object msg:this.transferContents()) {
                synchronized (this) {
                    transfer(this.transferType(),msg);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public boolean transfer(MonitorTransferTypeEnum transferTypeEnum,Object msg){
        boolean isSend =false;
        String jsonMsg= (msg instanceof String)?(String)msg:JSON.toJSONString(msg);
        if (monitorExpressWayClient.checkAndConnect(host,port)) {
            isSend = monitorExpressWayClient.send(transferTypeEnum.name()+":"+jsonMsg);
            log.info("[MonitorMe client] Transfer send type:{} result:{}",transferTypeEnum.name(),isSend);
        }
        return isSend;
    }
    public abstract MonitorTransferTypeEnum transferType();

    public abstract List<Object> transferContents();

}
