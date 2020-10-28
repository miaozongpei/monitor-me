package com.m.monitor.me.client.transfer.schedule;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class MonitorIntegratorTrucks implements CommandLineRunner {
    @Resource
    private IntegratorTruckRunnable integratorTruckRunnable;
    @Resource
    private HeartBeatTruckRunnable heartBeatTruckRunnable;
    @Resource
    private ClearTempPointMapRunnable clearTempPointMapRunnable;

    @Override
    public void run(String... args) throws Exception {
        ScheduledExecutorService  scheduledThreadPool = new ScheduledThreadPoolExecutor(20);
        try {
            //每隔15S发送一次心跳
            scheduledThreadPool.scheduleAtFixedRate(heartBeatTruckRunnable,0,13, TimeUnit.SECONDS);

            //每隔每隔11S开启传输监控聚合器通道
            scheduledThreadPool.scheduleAtFixedRate(integratorTruckRunnable,5,11, TimeUnit.SECONDS);

            //每隔30S清理一次临时监控点
            scheduledThreadPool.scheduleAtFixedRate(clearTempPointMapRunnable,10,30, TimeUnit.SECONDS);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
