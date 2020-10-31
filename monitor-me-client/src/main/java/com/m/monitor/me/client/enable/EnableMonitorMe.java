package com.m.monitor.me.client.enable;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 开启监控注解
 * @Author: miaozp
 * @Date: 2020/10/31 5:32 下午
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({MonitorDefinedClassImportSelector.class})
public @interface EnableMonitorMe {
}