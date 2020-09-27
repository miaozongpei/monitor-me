package com.m.monitor.me.client.transfer.schedule;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class MonitorIntegratorTrucks implements CommandLineRunner {
    @Resource
    private IntegratorTruckRunnable integratorTruckRunnable;
    @Override
    public void run(String... args) throws Exception {
        ScheduledExecutorService  scheduledThreadPool = new ScheduledThreadPoolExecutor(5);
        scheduledThreadPool.scheduleAtFixedRate(integratorTruckRunnable,10,15, TimeUnit.SECONDS);
    }
}
