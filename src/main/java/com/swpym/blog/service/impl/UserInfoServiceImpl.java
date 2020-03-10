package com.swpym.blog.service.impl;

import com.swpym.blog.dao.UserInfoDao;
import com.swpym.blog.pojo.UserInfo;
import com.swpym.blog.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author panym
 * @Description: ${todo}
 * @date 9:56  2020/3/10
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public List<UserInfo> findAll() {
        return userInfoDao.findAll();
    }

    @Override
    public void addUser(UserInfo userInfo) {
        userInfoDao.save(userInfo);
    }

    @Override
    public UserInfo findAccountInfoByUsername(String username) {
        return userInfoDao.findAccountInfoByUsername(username);
    }
}