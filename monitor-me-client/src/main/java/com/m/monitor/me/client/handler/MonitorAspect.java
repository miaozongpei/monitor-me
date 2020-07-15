package com.m.monitor.me.client.handler;
 
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class MonitorAspect {
    @Autowired
    private MonitorHandler monitorHandler;
    /**
     * 定义切入点，切入点为com.example.demo.aop.AopController中的所有函数
     *通过@Pointcut注解声明频繁使用的切点表达式
     */
    @Pointcut("execution(public * com.m.monitor.me.*.service.*.*(..)))")
    public void brokerAspect(){
    }
 
    /**
    * @description  在连接点执行之前执行的通知
    */
    @Before("brokerAspect()")
    public void doBefore(JoinPoint point){

    }

    @Around("brokerAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MonitorContext context=new MonitorContext(joinPoint);
        monitorHandler.doBefore(context);
        Object result=joinPoint.proceed();
        monitorHandler.doAfter(context);
        return result;
    }
 
    /**
     * @description  在连接点执行之后执行的通知（返回通知）
     */
    @AfterReturning("brokerAspect()")
    public void doAfterReturning(JoinPoint point){
    }
 
    /**
     * @description  在连接点执行之后执行的通知（异常通知）
     */
    @AfterThrowing("brokerAspect()")
    public void doAfterThrowing(JoinPoint point){
    }
    /**
     * @description  在连接点执行之后执行的通知（返回通知和异常通知的异常）
     */
    @After("brokerAspect()")
    public void doAfter(JoinPoint point){

    }
}