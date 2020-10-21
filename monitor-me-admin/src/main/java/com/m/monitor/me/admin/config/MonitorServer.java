package com.m.monitor.me.admin.config;

import com.m.monitor.me.service.transfer.MonitorExpressWayServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MonitorServer implements CommandLineRunner {
    @Value("${monitor.me.admin.port:8899}")
    private int port=8899;
    @Resource
    private MonitorExpressWayServer monitorExpressWayServer;
    @Override
    public void run(String... args) throws Exception {
        monitorExpressWayServer.bind(port);
    }
}