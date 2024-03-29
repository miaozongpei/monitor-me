package com.m.monitor.me.client.transfer.task;

import com.alibaba.fastjson.JSON;
import com.m.monitor.me.client.config.MonitorClientConfig;
import com.m.monitor.me.client.transfer.client.MonitorExpressWayClient;
import com.m.monitro.me.common.enums.MonitorTransferTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.List;
/**
 * 抽象传输任务类
 * @Author: miaozp
 * @Date: 2020/10/31 6:42 下午
 **/
@Slf4j
public abstract class AbstractTruckTask implements Runnable {
    @Value("${monitor.me.admin.host:localhost}")
    private String host="localhost";

    @Value("${monitor.me.admin.port:8899}")
    private int port=8899;
    @Resource
    private MonitorExpressWayClient monitorExpressWayClient;

    @Resource
    private MonitorClientConfig monitorClientConfig;

    @Override
    public void run() {
        if (!monitorClientConfig.isEnable){
            return;
        }

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
    /**
     * 定义传输类型
     * @Author: miaozp
     * @Date: 2020/10/31 6:43 下午
     * @return: com.m.monitro.me.common.enums.MonitorTransferTypeEnum
     **/
    public abstract MonitorTransferTypeEnum transferType();

    /**
     * 定义传输对象集合
     * @Author: miaozp
     * @Date: 2020/10/31 6:43 下午
     * @return: java.util.List<java.lang.Object>
     **/
    public abstract List<Object> transferContents();

}
