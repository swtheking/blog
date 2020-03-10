package com.swpym.blog.service.impl;

import com.swpym.blog.annotation.OperationLogDetail;
import com.swpym.blog.dao.UserDao;
import com.swpym.blog.enums.OperationType;
import com.swpym.blog.enums.OperationUnit;
import com.swpym.blog.pojo.User;
import com.swpym.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author panym
 * @Description: ${用户信息Service层实现类}
 * @date 14:18  2020/3/9
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @Cacheable(value = "myToken", key = "#username")
    @Override
    public User findAccountInfoByUsername(String username) {
        return userDao.findAccountInfoByUsername(username);
    }

    @OperationLogDetail(detail = "测试信息",level = 3,operationUnit = OperationUnit.USER, operationType = OperationType.UNKNOWN)
    @Override
    public void test() {
        System.out.println("哈哈哈哈哈哈哈哈哈哈哈");
    }
}
