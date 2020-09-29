package com.m.monitor.me.client.transfer.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.m.monitor.me.client.point.limit.PointLimitConfig;
import com.m.monitro.me.common.enums.MonitorTransferTypeEnum;
import com.m.monitro.me.common.limit.PointLimit;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

@Slf4j
public class MonitorExpressWayClient extends AbstractExpressWayClient {

    private static MonitorExpressWayClient monitorExpressWayClient=new MonitorExpressWayClient();
    private MonitorExpressWayClient() {
    }
    public static MonitorExpressWayClient getInstance(){
        return monitorExpressWayClient;
    }
    public MonitorExpressWayClient connect(String host,int port) {
        try {
            this.asyConnect(host,port);
            this.waitingConnect();
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }
    @Override
    public void replay(ChannelHandlerContext ctx, String msg) {
        log.info("[MonitorMe client] replay msg:{}",msg);
        if (!StringUtils.isEmpty(msg)){
            String type= msg.split(":")[0];
            if (MonitorTransferTypeEnum.POINT_LIMIT_PUSH.name().equals(type)){
                msg=msg.replaceFirst(MonitorTransferTypeEnum.POINT_LIMIT_PUSH.name()+":","");
                Map<String, PointLimit> limitMap = JSONObject.parseObject(msg, new TypeReference<Map<String, PointLimit>>(){});
                if (!CollectionUtils.isEmpty(limitMap)) {
                    PointLimitConfig.limitConfig.putAll(limitMap);
                }
            }
        }
    }
}
