package com.m.monitor.me.service.transfer.server.task;

import com.m.monitor.me.service.mogodb.base.BaseMongoService;
import com.m.monitor.me.service.mogodb.norm.MonitorPointService;
import com.m.monitor.me.service.mogodb.norm.NormMinuteService;
import com.m.monitor.me.service.mogodb.norm.NormSecondService;
import com.m.monitor.me.service.transfer.server.builder.IntegratorNormBuilder;
import com.m.monitor.me.service.transfer.server.norm.MethodNorm;
import com.m.monitor.me.service.transfer.server.record.MonitorPointRecord;
import com.m.monitro.me.common.transfer.IntegratorContext;
import com.m.monitro.me.common.utils.DateUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class IntegratorSaveTask {
    @Resource
    private NormMinuteService normMinuteService;
    @Resource
    private NormSecondService normSecondService;
    @Resource
    private MonitorPointService monitorPointService;


    private ExecutorService tasks;
    private static IntegratorSaveTask integratorSaveTask=new IntegratorSaveTask();
    private IntegratorSaveTask(){
        tasks=new ThreadPoolExecutor(1, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(50));
    }
    public static IntegratorSaveTask getInstance(){
        return integratorSaveTask;
    }

    public void save(IntegratorContext integratorContext){
        tasks.execute(new Runnable() {
            @Override
            public void run() {
                IntegratorNormBuilder integratorNormBuilder=new IntegratorNormBuilder().build(integratorContext);
                normSecondService.save(integratorNormBuilder.getSecondRecord());
                normMinuteService.saveOrModify(integratorNormBuilder.getMinuteRecord());

                monitorPointService.saveOrModify(integratorNormBuilder.getMonitorPointRecord());
            }
        });
    }

}
