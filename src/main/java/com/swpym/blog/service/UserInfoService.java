package com.swpym.blog.service;

import com.swpym.blog.pojo.UserInfo;

import java.util.List;

/**
 * @author panym
 * @Description: ${用户信息Service层}
 * @date 9:56  2020/3/10
 */
public interface UserInfoService {

    List<UserInfo> findAll();

    void addUser(UserInfo userInfo);

    /**
     * @param username:用户账号
     * @return 用户账号信息
     */
    UserInfo findAccountInfoByUsername(String username);

    UserInfo checkLogin(String username, String password);
}
