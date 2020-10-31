package com.m.monitor.me.client.config;

import com.m.monitor.me.client.point.MonitorInterceptor;
import com.m.monitor.me.client.transfer.client.MonitorExpressWayClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 监控客户端配置类
 * @Author: miaozp
 * @Date: 2020/10/31 5:28 下午
 **/
@Configuration
@Slf4j
public class MonitorClientConfig {
    @Value("${monitor.me.interceptor.package.name:com}")
    private String interceptorPackage="com";
    /**
     * 默认AOP拦截切面
     * @Author: miaozp
     * @Date: 2020/10/31 5:28 下午
     * @return: org.springframework.aop.support.DefaultPointcutAdvisor
     **/
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
    /**
     * 监控点拦截器
     * @Author: miaozp
     * @Date: 2020/10/31 5:29 下午
     * @return: com.m.monitor.me.client.point.MonitorInterceptor
     **/
    @Bean
    public MonitorInterceptor monitorInterceptor() {
        return new MonitorInterceptor();
    }
    /**
     * 监控传输通道客户端
     * @Author: miaozp
     * @Date: 2020/10/31 5:32 下午
     * @return: com.m.monitor.me.client.transfer.client.MonitorExpressWayClient
     **/
    @Bean
    public MonitorExpressWayClient monitorExpressWayClient() {
        return MonitorExpressWayClient.getInstance();
    }

}
