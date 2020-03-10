package com.swpym.blog.service.impl;

import com.swpym.blog.dao.OperationLogDao;
import com.swpym.blog.pojo.OperationLog;
import com.swpym.blog.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: shaowei
 * @date: 2020-03-10 9:47
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogDao operationLogDao;

    @Override
    public void save(OperationLog operationLog) {
        operationLogDao.save(operationLog);
    }
}
