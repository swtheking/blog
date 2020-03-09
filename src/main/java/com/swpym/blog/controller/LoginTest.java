package com.swpym.blog.controller;

import com.swpym.blog.annotation.UserLoginToken;
import com.swpym.blog.api.model.BaseResponse;
import com.swpym.blog.api.model.LoginParam;
import com.swpym.blog.common.util.JWTUtil;
import com.swpym.blog.pojo.User;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: shaowei
 * @date: 2020-03-09 14:18
 */
@RestController
public class LoginTest {

    private static final Logger _logger = LoggerFactory.getLogger(LoginTest.class);

    @PostMapping("/login")
//    @UserLoginToken
    public BaseResponse<String> login(@RequestHeader(name="Content-Type", defaultValue = "application/json") String contentType,
                                      @RequestBody LoginParam loginParam) {
        _logger.info("用户请求登录获取Token");
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();
        // 获取用户信息 到数据库
        String encodedPassword = "123456";
        if (password.equals("123456")) {
            return new BaseResponse<>(true, "Login success", JWTUtil.sign(username, encodedPassword));
        } else {
            throw new UnauthorizedException();
        }
    }

    @PostMapping("/find")
    @UserLoginToken
    public User find(){
        return new User();
    }


}
