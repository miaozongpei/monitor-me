package com.m.monitor.me.service.transfer.server;

import com.alibaba.fastjson.JSON;
import com.m.monitor.me.service.mogodb.record.MonitorPointRecord;
import com.m.monitor.me.service.mogodb.service.MonitorPointService;
import com.m.monitor.me.service.transfer.task.IntegratorSaveTask;
import com.m.monitro.me.common.enums.MonitorTransferTypeEnum;
import com.m.monitro.me.common.limit.PointLimit;
import com.m.monitro.me.common.transfer.IntegratorContext;
import com.m.monitro.me.common.utils.MonitorConstant;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 传输通道服务端
 * @Author: miaozp
 * @Date: 2020/10/31 5:01 下午
 **/
@Slf4j
public class MonitorExpressWayServer extends AbstractExpressWayServer {

    private static MonitorExpressWayServer monitorExpressWayServer = new MonitorExpressWayServer();
    @Resource
    private IntegratorSaveTask integratorSaveTask;

    @Resource
    private MonitorPointService monitorPointService;

    private MonitorExpressWayServer() {
    }

    public static MonitorExpressWayServer getInstance() {
        return monitorExpressWayServer;
    }
    /**
     * 接收到客户端传输的消息
     * @Author: miaozp
     * @Date: 2020/10/31 5:03 下午
     * @param ctx: 通道上下文
     * @param msg: 消息
     * @return: boolean
     **/
    @Override
    public boolean receive(ChannelHandlerContext ctx, String msg) {
        log.info("[MonitorMe admin] receive msg:{}", msg);
        if (!StringUtils.isEmpty(msg)) {
            String type = msg.split(MonitorConstant.MONITOR_EXPRESSWAY_MSG_SPLIT)[0];
            //监控聚合指标
            if (MonitorTransferTypeEnum.POINT_INTEGRATOR.name().equals(type)) {
                saveMonitorIntegrator(ctx, msg.replaceFirst(MonitorTransferTypeEnum.POINT_INTEGRATOR.name() + MonitorConstant.MONITOR_EXPRESSWAY_MSG_SPLIT, ""));
            //心跳
            } else if (MonitorTransferTypeEnum.HEART_BEAT.name().equals(type)) {
                synMonitorPointLimit(ctx, msg);
            }
        }
        return true;
    }
    /**
     * 保存监控聚合指标
     * @Author: miaozp
     * @Date: 2020/10/31 5:05 下午
     * @param ctx:
     * @param msg:
     * @return: void
     **/
    private void saveMonitorIntegrator(ChannelHandlerContext ctx, String msg) {
        IntegratorContext integratorContext = JSON.parseObject(msg, IntegratorContext.class);
        integratorContext.setHost(getHostAddress(ctx));
        integratorSaveTask.save(integratorContext);
    }

    /**
     * 收到心跳，将服务端对监控点设置的限流规则推送至客户端
     * @Author: miaozp
     * @Date: 2020/10/31 5:06 下午
     * @param ctx:
     * @param msg:
     * @return: void
     **/
    private void synMonitorPointLimit(ChannelHandlerContext ctx, String msg) {
        String host = getHostAddress(ctx);
        String[] msgArr = msg.split(MonitorConstant.MONITOR_EXPRESSWAY_MSG_SPLIT);
        if (msgArr.length < MonitorConstant.MONITOR_EXPRESSWAY_MSG_ARR_MIN_LEN) {
            return;
        }
        List<MonitorPointRecord> needingSynPoints = monitorPointService.queryPointLimit(msgArr[1], host);
        if (CollectionUtils.isEmpty(needingSynPoints)) {
            return;
        }
        Map<String, PointLimit> map = new HashMap<>(20);
        for (MonitorPointRecord record : needingSynPoints) {
            map.put(record.getM(), record.getMl());
        }
        ctx.writeAndFlush(MonitorTransferTypeEnum.POINT_LIMIT_PUSH + MonitorConstant.MONITOR_EXPRESSWAY_MSG_SPLIT + JSON.toJSONString(map));
    }

    /**
     * 获取客户点IP地址
     *
     * @Author: miaozp
     * @Date: 2020/10/31 4:11 下午
     * @Param: [ctx]
     * @Return: java.lang.String
     **/
    private String getHostAddress(ChannelHandlerContext ctx) {
        return ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();
    }
}
