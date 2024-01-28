package com.w2m.app.annotations;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TimedAspect {
    @Around("@within(RuntimeLog) || @annotation(RuntimeLog)")
    public Object timeCalculation(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("Class: {} with method: {} has been executed in: {} milliseconds", joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName(), endTime - startTime);
        return result;

    }
}

