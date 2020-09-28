package com.m.monitor.me.client.transfer.client;

import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Value;

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
        System.out.println(ctx);
    }
}
