package com.m.monitor.me.service.transfer;

import com.alibaba.fastjson.JSON;
import com.m.monitor.me.service.mogodb.norm.MonitorPointService;
import com.m.monitor.me.service.transfer.record.MonitorPointRecord;
import com.m.monitor.me.service.transfer.task.IntegratorSaveTask;
import com.m.monitro.me.common.enums.MonitorTransferTypeEnum;
import com.m.monitro.me.common.enums.PointLimitSynStatusEnum;
import com.m.monitro.me.common.limit.PointLimit;
import com.m.monitro.me.common.transfer.IntegratorContext;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MonitorExpressWayServer extends AbstractExpressWayServer {
    private static MonitorExpressWayServer monitorExpressWayServer=new MonitorExpressWayServer();
    @Resource
    private IntegratorSaveTask integratorSaveTask;
    private MonitorExpressWayServer() {
    }
    public static MonitorExpressWayServer getInstance(){
        return monitorExpressWayServer;
    }
    @Override
    public boolean receive(ChannelHandlerContext ctx, String msg) {
        log.info("[MonitorMe admin] receive msg:{}",msg);
        if (!StringUtils.isEmpty(msg)){
            String type= msg.split(":")[0];
            if (MonitorTransferTypeEnum.POINT_INTEGRATOR.name().equals(type)){
                saveMonitorIntegrator(ctx,msg.replaceFirst(MonitorTransferTypeEnum.POINT_INTEGRATOR.name()+":",""));
            }else if (MonitorTransferTypeEnum.HEART_BEAT.name().equals(type)){
                synMonitorPointLimit(ctx,msg);
            }
        }
        return true;
    }

    private void saveMonitorIntegrator(ChannelHandlerContext ctx, String msg){
        IntegratorContext integratorContext=JSON.parseObject(msg, IntegratorContext.class);
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        integratorContext.setHost(insocket.getAddress().getHostAddress());
        integratorSaveTask.save(integratorContext);
    }


    @Resource
    private MonitorPointService monitorPointService;
    private void synMonitorPointLimit(ChannelHandlerContext ctx, String msg){
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String host=insocket.getAddress().getHostAddress();
        String[] msgArr= msg.split(":");
        if (msgArr.length<2){
            return;
        }
        String name=msgArr[1];
        List<MonitorPointRecord> needingSynPoints=monitorPointService.queryPointLimit(name,host);
        if (CollectionUtils.isEmpty(needingSynPoints)){
            return;
        }
        Map<String, PointLimit> map=new HashMap<>();
        for (MonitorPointRecord record:needingSynPoints){
            map.put(record.getM(),record.getMl());
        }
        ctx.writeAndFlush(MonitorTransferTypeEnum.POINT_LIMIT_PUSH+":"+JSON.toJSONString(map));
    }
}
