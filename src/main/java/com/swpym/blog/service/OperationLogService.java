package com.swpym.blog.service;

import com.swpym.blog.pojo.OperationLog;

/**
 * @description:
 * @author: shaowei
 * @date: 2020-03-10 9:47
 */
public interface OperationLogService {

    /*
     * @description: 保存
     * @author: shaowei
     * @date: 2020-03-10 09:47:34
     * @param operationLog
     * @return: void
     */
    void save(OperationLog operationLog);
}
