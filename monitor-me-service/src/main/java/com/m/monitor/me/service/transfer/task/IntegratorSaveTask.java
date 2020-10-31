package com.m.monitor.me.service.transfer.task;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.m.monitor.me.service.mogodb.service.*;
import com.m.monitor.me.service.mogodb.service.norm.NormDayService;
import com.m.monitor.me.service.mogodb.service.norm.NormHourService;
import com.m.monitor.me.service.mogodb.service.norm.NormMinuteService;
import com.m.monitor.me.service.mogodb.service.norm.NormSecondService;
import com.m.monitor.me.service.transfer.builder.IntegratorNormBuilder;
import com.m.monitor.me.service.mogodb.record.MonitorPointRecord;
import com.m.monitro.me.common.transfer.IntegratorContext;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * 聚合指标保存认为
 * @Author: miaozp
 * @Date: 2020/10/31 4:03 下午
 **/
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
        tasks=new ThreadPoolExecutor(1, 50,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(200),
                new ThreadFactoryBuilder().setNameFormat("IntegratorSaveTask-pool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy()
                );
    }
    public static IntegratorSaveTask getInstance(){
        return integratorSaveTask;
    }
    /**
     * 通过线程存储集合器指标信息
     * @Author: miaozp
     * @Date: 2020/10/31 4:05 下午
     * @Param: [integratorContext]
     * @Return: void
     **/
    public void save(IntegratorContext integratorContext){
        tasks.execute(new Runnable() {
            @Override
            public void run() {
                IntegratorNormBuilder integratorNormBuilder=new IntegratorNormBuilder(integratorContext);
                integratorNormBuilder.build();
                //存储秒数级别监控指标
                normSecondService.save(integratorNormBuilder.getSecondRecord());
                //存储分钟级别监控指标
                normMinuteService.saveOrModify(integratorNormBuilder);
                //存储小时级别监控指标
                normHourService.saveOrModify(integratorNormBuilder);
                //存储天数级别监控指标
                normDayService.saveOrModify(integratorNormBuilder);

                //存储监控系统
                monitorHostService.saveOrModify(integratorNormBuilder.getMonitorHostRecord());
                //存储监控点
                saveMonitorPointMc(integratorContext);
            }
        });
    }

    /**
     * 保存监控点
     * @Author: miaozp
     * @Date: 2020/10/31 4:08 下午
     * @Param: [integratorContext]
     * @Return: void
     **/
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
