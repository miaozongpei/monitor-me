package com.m.monitor.me.client.transfer.client;

import io.netty.channel.ChannelHandlerContext;

public class MonitorExpressWayClient extends AbstractExpressWayClient {
    private String host="localhost";
    private int port=8899;
    private static MonitorExpressWayClient monitorExpressWayClient=new MonitorExpressWayClient();
    private MonitorExpressWayClient() {
        try {
            this.connect(host,port);
            this.waitingConnect();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static MonitorExpressWayClient getInstance(){
        return monitorExpressWayClient;
    }
    @Override
    public void replay(ChannelHandlerContext ctx, String msg) {

    }
}
