package com.m.monitor.me.client.enable;

import com.m.monitor.me.client.config.MonitorClientConfig;
import com.m.monitor.me.client.point.MonitorHandler;
import com.m.monitor.me.client.transfer.schedule.ClearTempPointMapRunnable;
import com.m.monitor.me.client.transfer.schedule.HeartBeatTruckRunnable;
import com.m.monitor.me.client.transfer.schedule.IntegratorTruckRunnable;
import com.m.monitor.me.client.transfer.schedule.MonitorIntegratorTrucks;
import com.m.monitro.me.common.utils.ClassUtil;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MonitorDefinedClassImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                ClassUtil.classTypeForStr(MonitorClientConfig.class),
                ClassUtil.classTypeForStr(MonitorIntegratorTrucks.class),
                ClassUtil.classTypeForStr(IntegratorTruckRunnable.class),
                ClassUtil.classTypeForStr(HeartBeatTruckRunnable.class),
                ClassUtil.classTypeForStr(ClearTempPointMapRunnable.class),
                ClassUtil.classTypeForStr(MonitorHandler.class)
        };
    }
}