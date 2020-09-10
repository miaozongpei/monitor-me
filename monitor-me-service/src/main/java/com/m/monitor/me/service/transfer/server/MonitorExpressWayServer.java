package com.m.monitor.me.service.transfer.server;

import com.alibaba.fastjson.JSON;
import com.m.monitor.me.service.transfer.server.task.IntegratorSaveTask;
import com.m.monitro.me.common.transfer.IntegratorContext;
import com.m.monitro.me.common.utils.TransferSnappyUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
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
        IntegratorContext integratorContext=JSON.parseObject(msg, IntegratorContext.class);
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        integratorContext.setHost(insocket.getAddress().getHostAddress());
        integratorSaveTask.save(integratorContext);
        return true;
    }
}
