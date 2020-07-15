package com.m.monitor.me.client.enable;

import com.m.monitor.me.client.handler.MonitorAspect;
import com.m.monitor.me.client.handler.MonitorHandler;
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