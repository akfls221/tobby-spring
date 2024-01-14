package com.example.tobbyspring.basic.aspecttest;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class LogAopTest {

    @Pointcut(value = "execution(* com.example.tobbyspring.basic.aspecttest..*.*(..))")
    private void pointCut() {
    }

    @Around("pointCut()")
    public Object aroundLog(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringTypeName();
        Method method = methodSignature.getMethod();
        log.info("{} {} start", className, method.getName());

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.info("arg type : {}", arg.getClass().getSimpleName());
            log.info("arg value : {}", arg);
        }

        Object result = joinPoint.proceed();

        log.info("result : {}", result);
        log.info("{} {} end", className, method.getName());

        return joinPoint.proceed();
    }
}
