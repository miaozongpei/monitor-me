package com.m.monitor.me.admin.config;

import com.m.monitor.me.service.transfer.server.MonitorExpressWayServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MonitorServer implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
       MonitorExpressWayServer.getInstance().bind();
    }
}