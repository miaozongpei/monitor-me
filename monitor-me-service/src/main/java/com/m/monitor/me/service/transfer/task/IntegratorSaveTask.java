package com.m.monitor.me.service.transfer.task;

import com.m.monitor.me.service.mogodb.norm.*;
import com.m.monitor.me.service.transfer.builder.IntegratorNormBuilder;
import com.m.monitor.me.service.transfer.record.MonitorPointRecord;
import com.m.monitro.me.common.transfer.IntegratorContext;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class IntegratorSaveTask {

    @Resource
    private NormSecondService normSecondService;

    @Resource
    private NormMinuteService normMinuteService;
    @Resource
    private NormHourService normHourService;
    @Resource
    private NormDayService normDayService;


    @Resource
    private MonitorPointService monitorPointService;
    @Resource
    private MonitorHostService monitorHostService;


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
                IntegratorNormBuilder integratorNormBuilder=new IntegratorNormBuilder(integratorContext);
                integratorNormBuilder.build();
                normSecondService.save(integratorNormBuilder.getSecondRecord());

                normMinuteService.saveOrModify(integratorNormBuilder);
                normHourService.saveOrModify(integratorNormBuilder);
                normDayService.saveOrModify(integratorNormBuilder);


                monitorHostService.saveOrModify(integratorNormBuilder.getMonitorHostRecord());
                //保存监控点
                saveMonitorPointMc(integratorContext);
            }
        });
    }

    //保存监控点
    private void saveMonitorPointMc(IntegratorContext integratorContext){
        String name=integratorContext.getName();
        String host=integratorContext.getHost();
        for (String m:integratorContext.getMcs().keySet()){
            String mc=integratorContext.getMcs().get(m);
            if (!StringUtils.isEmpty(mc)) {
                monitorPointService.saveOrModifyMc(new MonitorPointRecord(name, host, m, mc));
            }
        }
    }



}
