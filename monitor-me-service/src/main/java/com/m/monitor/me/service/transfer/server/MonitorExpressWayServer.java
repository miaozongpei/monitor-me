package com.m.monitor.me.service.transfer.server;

import com.alibaba.fastjson.JSON;
import com.m.monitor.me.service.mogodb.base.BaseMongoService;
import com.m.monitro.me.common.utils.DateUtil;
import io.netty.channel.ChannelHandlerContext;
import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MonitorExpressWayServer extends AbstractExpressWayServer {
    private int port=8899;
    private static MonitorExpressWayServer monitorExpressWayServer=new MonitorExpressWayServer();
    @Resource
    private BaseMongoService baseMongoService;
    private MonitorExpressWayServer() {
    }
    public static MonitorExpressWayServer getInstance(){
        return monitorExpressWayServer;
    }
    public void bind(){
        try {
            bind(port);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean receive(ChannelHandlerContext ctx, String msg) {
        System.out.println(msg);
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = insocket.getAddress().getHostAddress();
        Map integrator=new HashMap();
        integrator.put(ip,JSON.parseObject(msg, LinkedHashMap.class));
        baseMongoService.insert(DateUtil.parseDate(new Date(),DateUtil.FORMAT_YYYYMM),integrator);
        return true;
    }
}
