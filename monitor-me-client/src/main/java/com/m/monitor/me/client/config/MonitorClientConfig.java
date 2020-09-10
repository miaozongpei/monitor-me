package com.m.monitor.me.client.config;

import com.m.monitor.me.client.point.MonitorInterceptor;
import com.m.monitor.me.client.transfer.client.MonitorExpressWayClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MonitorClientConfig {
    @Value("${monitor.me.interceptor.package.name:com}")
    private String interceptorPackage="com";

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        String expression="execution(public * "+interceptorPackage+"..*.*(..)))";
        pointcut.setExpression(expression);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(monitorInterceptor());
        log.info("loading DefaultPointcutAdvisor interceptorPackage:{},advisor：{} ",interceptorPackage,advisor.toString());
        return advisor;
    }
    @Bean
    public MonitorInterceptor monitorInterceptor() {
        return new MonitorInterceptor();
    }
    @Bean
    public MonitorExpressWayClient monitorExpressWayClient() {

        return MonitorExpressWayClient.getInstance();
    }

}
