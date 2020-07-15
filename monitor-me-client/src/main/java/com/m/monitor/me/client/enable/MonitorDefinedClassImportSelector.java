package com.m.monitor.me.client.enable;

import com.m.monitor.me.client.point.MonitorAspect;
import com.m.monitor.me.client.point.MonitorHandler;
import com.m.monitro.me.common.utils.ClassUtil;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MonitorDefinedClassImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                ClassUtil.classTypeForStr(MonitorAspect.class),
                ClassUtil.classTypeForStr(MonitorHandler.class)
        };
    }
}