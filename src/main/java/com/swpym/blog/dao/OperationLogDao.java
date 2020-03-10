package com.swpym.blog.dao;

import com.swpym.blog.pojo.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: shaowei
 * @date: 2020-03-10 9:45
 */
@Repository
public interface OperationLogDao extends JpaRepository<OperationLog,String> {

}
