package com.m.monitor.me.admin.config;

import com.m.monitor.me.service.transfer.server.MonitorExpressWayServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MonitorServer implements CommandLineRunner {
    @Resource
    private MonitorExpressWayServer monitorExpressWayServer;
    @Override
    public void run(String... args) throws Exception {
        monitorExpressWayServer.bind();
    }
}