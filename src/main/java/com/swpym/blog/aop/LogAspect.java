package com.swpym.blog.aop;

import com.alibaba.fastjson.JSON;
import com.swpym.blog.annotation.OperationLogDetail;
import com.swpym.blog.pojo.OperationLog;
import com.swpym.blog.service.OperationLogService;
import lombok.extern.slf4j.Slf4j;
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
                this.addOperationLog(joinPoint, operationLogDetail, time);
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
    private void addOperationLog(JoinPoint joinPoint, OperationLogDetail res, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        OperationLog operationLog = new OperationLog();
        operationLog.setRunTime(time);
        operationLog.setReturnValue(JSON.toJSONString(res));
        operationLog.setId(UUID.randomUUID().toString());
        operationLog.setCreateTime(new Date());
        operationLog.setMethod(signature.getDeclaringTypeName() + "." + signature.getName());
        // TODO 从token中获取用户信息
        operationLog.setUserId("用户ID");
        operationLog.setUserName("用户名");
        if (res != null) {
            operationLog.setLevel(res.level());
            operationLog.setDescribe(getDetail(((MethodSignature) joinPoint.getSignature()).getParameterNames(), joinPoint.getArgs(), res));
            operationLog.setOperationType(res.operationType().getValue());
            operationLog.setOperationUnit(res.operationUnit().getValue());
        }
        this.operationLogService.save(operationLog);
    }

    /**
     * 对当前登录用户和占位符处理
     *
     * @param argNames   方法参数名称数组
     * @param args       方法参数数组
     * @param annotation 注解信息
     * @return 返回处理后的描述
     */
    private String getDetail(String[] argNames, Object[] args, OperationLogDetail annotation) {
        Map<Object, Object> map = new HashMap<>(4);
        for (int i = 0; i < argNames.length; i++) {
            map.put(argNames[i], args[i]);
        }
        String detail = annotation.detail();
        try {
            detail = annotation.detail();
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                Object k = entry.getKey();
                Object v = entry.getValue();
                detail = detail.replace("{{" + k + "}}", JSON.toJSONString(v));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

}
