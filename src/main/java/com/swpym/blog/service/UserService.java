package com.swpym.blog.service;

import com.swpym.blog.pojo.User;

import java.util.List;

/**
 * @author panym
 * @Description: ${用户信息Service层}
 * @date 14:18  2020/3/9
 */
public interface UserService {

    List<User>findAllUsers();

    User findAccountInfoByUsername(String username);

    void test();
}
