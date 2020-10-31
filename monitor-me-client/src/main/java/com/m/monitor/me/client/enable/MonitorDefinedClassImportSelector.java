package com.m.monitor.me.client.enable;

import com.m.monitor.me.client.config.MonitorClientConfig;
import com.m.monitor.me.client.point.MonitorHandler;
import com.m.monitor.me.client.transfer.task.runner.MonitorIntegratorTrucksRunner;
import com.m.monitor.me.client.transfer.task.ClearTempPointMapTask;
import com.m.monitor.me.client.transfer.task.HeartBeatTruckTask;
import com.m.monitor.me.client.transfer.task.IntegratorTruckTask;
import com.m.monitro.me.common.utils.ClassUtil;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
/**
 * Springboot启动导入选择器，容器中的类导入到Spring容器中
 * @Author: miaozp
 * @Date: 2020/10/31 5:33 下午
 **/
public class MonitorDefinedClassImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                ClassUtil.classTypeForStr(MonitorClientConfig.class),
                ClassUtil.classTypeForStr(MonitorIntegratorTrucksRunner.class),
                ClassUtil.classTypeForStr(IntegratorTruckTask.class),
                ClassUtil.classTypeForStr(HeartBeatTruckTask.class),
                ClassUtil.classTypeForStr(ClearTempPointMapTask.class),
                ClassUtil.classTypeForStr(MonitorHandler.class)
        };
    }
}