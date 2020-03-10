package com.swpym.blog.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 日志切面
 */
@Aspect
@Component
@Slf4j
public class LogAspectService {

    @Pointcut("execution( * com.swpym.blog.service.impl.*.save(..)) " +
            "|| execution (* com.swpym.blog.service.impl.*.find(..))" +
            "|| execution (* com.swpym.blog.service.impl.*.update(..))" +
            "|| execution (* com.swpym.blog.service.impl.*.delete(..))")
    public void webLog() {
    }

    //环绕通知,环绕增强，相当于MethodInterceptor
    @Around("webLog()")
    public Object arround(ProceedingJoinPoint pjp) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("CLASS_METHOD : " + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
        log.info("ARGS : " + Arrays.toString(pjp.getArgs()));
        try {
            Object o = pjp.proceed();
            log.info("return value:" + o);
            return o;
        } catch (Throwable e) {
            throw e;
        }
    }

}