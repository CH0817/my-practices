package com.rex.practice.aop;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
public class LogAop {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Around("execution(public * com.rex.practice.service..*.*(..))")
    public Object loggerServiceAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String executeMethod = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();
        logger.info("execute method: {}()", executeMethod);
        if (ArrayUtils.isNotEmpty(joinPoint.getArgs())) {
            Arrays.stream(joinPoint.getArgs()).forEach(
                    arg -> logger.info("give method {}() argument: {}", executeMethod, arg));
        }
        Object result = joinPoint.proceed();
        logger.info("method {}(), return: {}", executeMethod, result);
        return result;
    }

    @Around("execution(public * com.rex.practice.web.controller..*.*(..))")
    public Object loggerControllerAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert Objects.nonNull(sra);
        HttpServletRequest request = sra.getRequest();
        logger.info("execute uri: {}, method: {}", request.getRequestURI(), request.getMethod());
        Object result = joinPoint.proceed();
        logger.info("uri: {}, return: {}", request.getRequestURI(), result);
        return result;
    }

    @AfterThrowing(value = "execution(public * com.rex.practice..*.*(..))", throwing = "exception")
    public void loggerException(JoinPoint joinPoint, Exception exception) {
        logger.error("execute {} occurred error!", joinPoint.getSignature(), exception);
    }

}
