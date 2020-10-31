package com.m.monitor.me.client.transfer.client;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.m.monitor.me.client.point.limit.PointLimitConfig;
import com.m.monitro.me.common.enums.MonitorTransferTypeEnum;
import com.m.monitro.me.common.limit.PointLimit;
import com.m.monitro.me.common.utils.MonitorConstant;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
/**
 * 传输通道客户端
 * @Author: miaozp
 * @Date: 2020/10/31 6:32 下午
 **/
@Slf4j
public class MonitorExpressWayClient extends AbstractExpressWayClient {

    private static MonitorExpressWayClient monitorExpressWayClient=new MonitorExpressWayClient();
    private MonitorExpressWayClient() {

    }
    public static MonitorExpressWayClient getInstance(){
        return monitorExpressWayClient;
    }
    @Override
    public void replay(ChannelHandlerContext ctx, String msg) {
        log.info("[MonitorMe client] replay msg:{}",msg);
        if (!StringUtils.isEmpty(msg)){
            String type= msg.split(MonitorConstant.MONITOR_EXPRESSWAY_MSG_SPLIT)[0];
            //监控点限制规则推送
            if (MonitorTransferTypeEnum.POINT_LIMIT_PUSH.name().equals(type)){
                msg=msg.replaceFirst(MonitorTransferTypeEnum.POINT_LIMIT_PUSH.name()+":","");
                Map<String, PointLimit> limitMap = JSONObject.parseObject(msg, new TypeReference<Map<String, PointLimit>>(){});
                if (!CollectionUtils.isEmpty(limitMap)) {
                    PointLimitConfig.limitConfig=limitMap;
                }else{
                    PointLimitConfig.limitConfig=PointLimitConfig.emptyConfig;
                }
            }
        }
    }
}
