package com.mt.marketdata.annotation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LatencyMeasurementAspect {

    private static final Logger log = LogManager.getLogger(LatencyMeasurementAspect.class);

    @Around("@annotation(LatencyMeasurement)")
    public Object latencyMeasurement(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Entry Point Timestamp:{}", System.nanoTime());
        Object obj = joinPoint.proceed();
        log.info("Exit Point Timestamp:{}", System.nanoTime());
        return obj;
    }
}
