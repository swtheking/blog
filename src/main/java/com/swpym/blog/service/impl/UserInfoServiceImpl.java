package com.swpym.blog.service.impl;

import com.swpym.blog.api.model.BaseResponse;
import com.swpym.blog.common.util.CookieUtil;
import com.swpym.blog.common.util.JWTUtil;
import com.swpym.blog.constant.UserSessionConst;
import com.swpym.blog.dao.UserInfoDao;
import com.swpym.blog.pojo.UserInfo;
import com.swpym.blog.service.UserInfoService;
import org.apache.shiro.authz.UnauthorizedException;
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

    @Override
    public UserInfo checkLogin(String username, String password) {
        UserInfo userInfo = userInfoDao.findAccountInfoByUsername(username);
        if (userInfo == null) {
            throw new UnauthorizedException("用户不存在");
        }
        String encodedPassword = userInfo.getPassword();
        if (password.equals(userInfo.getPassword())) {
            // 生成token
            String token = JWTUtil.sign(username, encodedPassword);
            // 加入cookie
            CookieUtil.addCookie(UserSessionConst.TOKEN_COOKIE, token, UserSessionConst.EXPIRE_SECONDS);
        } else {
            throw new UnauthorizedException("密码错误");
        }
        return userInfo;
    }
}
