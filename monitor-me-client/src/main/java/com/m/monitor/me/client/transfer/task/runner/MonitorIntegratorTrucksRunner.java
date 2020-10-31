package com.m.monitor.me.client.transfer.task.runner;

import com.m.monitor.me.client.transfer.task.ClearTempPointMapTask;
import com.m.monitor.me.client.transfer.task.HeartBeatTruckTask;
import com.m.monitor.me.client.transfer.task.IntegratorTruckTask;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * Springboot启动运行加载类
 * @Author: miaozp
 * @Date: 2020/10/31 6:52 下午
 **/
@Component
public class MonitorIntegratorTrucksRunner implements CommandLineRunner {
    @Resource
    private IntegratorTruckTask integratorTruckTask;
    @Resource
    private HeartBeatTruckTask heartBeatTruckTask;
    @Resource
    private ClearTempPointMapTask clearTempPointMapTask;

    @Override
    public void run(String... args) throws Exception {
        ScheduledExecutorService  scheduledThreadPool = new ScheduledThreadPoolExecutor(20);
        try {
            //每隔15S发送一次心跳
            scheduledThreadPool.scheduleAtFixedRate(heartBeatTruckTask,0,13, TimeUnit.SECONDS);

            //每隔每隔11S开启传输监控聚合器通道
            scheduledThreadPool.scheduleAtFixedRate(integratorTruckTask,5,11, TimeUnit.SECONDS);

            //每隔30S清理一次临时监控点
            scheduledThreadPool.scheduleAtFixedRate(clearTempPointMapTask,10,30, TimeUnit.SECONDS);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
