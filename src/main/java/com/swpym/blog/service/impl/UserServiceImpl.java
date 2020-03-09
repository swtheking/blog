package com.swpym.blog.service.impl;

import com.swpym.blog.dao.UserDao;
import com.swpym.blog.pojo.User;
import com.swpym.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author panym
 * @Description: ${todo}
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

    @Override
    public User findAccountInfoByUsername(String username) {
        return userDao.findAccountInfoByUsername(username);
    }
}
