package com.m.monitor.me.service.transfer.server;

import io.netty.channel.ChannelHandlerContext;

public class MonitorExpressWayServer extends AbstractExpressWayServer {
    private int port=8899;
    private static MonitorExpressWayServer monitorExpressWayServer=new MonitorExpressWayServer();
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
        return true;
    }
}
