package com.swpym.blog.aop;

import com.alibaba.fastjson.JSON;
import com.swpym.blog.annotation.OperationLogDetail;
import com.swpym.blog.common.util.CookieUtil;
import com.swpym.blog.common.util.JWTUtil;
import com.swpym.blog.constant.UserSessionConst;
import com.swpym.blog.pojo.OperationLog;
import com.swpym.blog.service.OperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @description: 日志处理
 * @author: shaowei
 * @date: 2020-03-09 15:46
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 环绕增强，相当于MethodInterceptor
     */
    @Around("@annotation(operationLogDetail)") // 改为只对改注解的日志
    public Object doAround(ProceedingJoinPoint joinPoint, OperationLogDetail operationLogDetail) throws Throwable {
        Object res = null;
        long time = System.currentTimeMillis();
        try {
            res = joinPoint.proceed();
            time = System.currentTimeMillis() - time;
            return res;
        } finally {
            try {
                //方法执行完成后增加日志
                this.addOperationLog(joinPoint, operationLogDetail, time, res);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * @description: 增加日志
     * @author: shaowei
     * @date: 2020-03-09 16:14:20
     * @param joinPoint
     * @param res
     * @param time
     * @return: void
     */
    private void addOperationLog(JoinPoint joinPoint, OperationLogDetail res, long time, Object ob) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        OperationLog operationLog = new OperationLog();
        operationLog.setRunTime(time);
        operationLog.setReturnValue(ob + "");
        operationLog.setId(UUID.randomUUID().toString());
        operationLog.setArgs(StringUtils.join(joinPoint.getArgs(), ","));
        operationLog.setCreateTime(new Date());
        operationLog.setMethod(signature.getDeclaringTypeName() + "." + signature.getName());
        String token = CookieUtil.getCookie(UserSessionConst.TOKEN_COOKIE);
        String username = JWTUtil.getUsername(token);
        operationLog.setUserName(username);
        if (res != null) {
            operationLog.setLevel(res.level());
            operationLog.setDescribe(res.detail());
            operationLog.setOperationType(res.operationType().getValue());
            operationLog.setOperationUnit(res.operationUnit().getValue());
        }
        this.operationLogService.save(operationLog);
    }

}
