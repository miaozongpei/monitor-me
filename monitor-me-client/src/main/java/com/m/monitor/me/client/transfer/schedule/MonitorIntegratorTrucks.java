package com.m.monitor.me.client.transfer.schedule;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class MonitorIntegratorTrucks implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        ScheduledExecutorService  scheduledThreadPool = Executors.newScheduledThreadPool(10);
        scheduledThreadPool.scheduleAtFixedRate(new IntegratorTruckRunnable(),10,5, TimeUnit.SECONDS);
    }
}
