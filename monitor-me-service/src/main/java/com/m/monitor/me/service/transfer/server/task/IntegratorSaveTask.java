package com.m.monitor.me.service.transfer.server.task;

import com.m.monitor.me.service.mogodb.base.BaseMongoService;
import com.m.monitor.me.service.mogodb.norm.*;
import com.m.monitor.me.service.transfer.server.builder.IntegratorNormBuilder;
import com.m.monitor.me.service.transfer.server.norm.MethodNorm;
import com.m.monitor.me.service.transfer.server.record.MonitorMethodChainRecord;
import com.m.monitor.me.service.transfer.server.record.MonitorPointRecord;
import com.m.monitro.me.common.transfer.IntegratorContext;
import com.m.monitro.me.common.utils.DateUtil;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
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


                monitorHostService.saveOrModify(integratorNormBuilder.getMonitorPointRecord());
                //保存监控点
                saveMonitorPoint(integratorContext);
            }
        });
    }

    //保存监控点
    private void saveMonitorPoint(IntegratorContext integratorContext){
        String name=integratorContext.getName();
        String host=integratorContext.getHost();
        for (String m:integratorContext.getMcs().keySet()){
            String mc=integratorContext.getMcs().get(m);
            if (!StringUtils.isEmpty(mc)) {
                monitorPointService.saveOrModify(new MonitorMethodChainRecord(name, host, m, mc));
            }
        }
    }

}
