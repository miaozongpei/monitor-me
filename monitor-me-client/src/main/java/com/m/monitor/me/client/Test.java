package com.m.monitor.me.client;

import com.m.monitor.me.client.transfer.client.MonitorExpressWayClient;
import lombok.SneakyThrows;

public class Test {
    public static void main(String[] args) throws Exception {
        MonitorExpressWayClient client= MonitorExpressWayClient.getInstance();
        client.send("你好！");
    }
}