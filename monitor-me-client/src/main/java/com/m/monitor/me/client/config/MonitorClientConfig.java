package com.m.monitor.me.client.config;

import com.m.monitor.me.client.point.MonitorInterceptor;
import com.m.monitor.me.client.transfer.client.MonitorExpressWayClient;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
