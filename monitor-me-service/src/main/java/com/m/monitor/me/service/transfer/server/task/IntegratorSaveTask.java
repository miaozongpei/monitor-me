package com.m.monitor.me.service.transfer.server.task;

import com.m.monitor.me.service.mogodb.base.BaseMongoService;
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
    private BaseMongoService baseMongoService;
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
                IntegratorRecord integratorRecord=new IntegratorRecord().build(integratorContext);
                baseMongoService.insert(DateUtil.parseDate(new Date(),DateUtil.FORMAT_YYYYMM),integratorRecord);
            }
        });
    }

}
