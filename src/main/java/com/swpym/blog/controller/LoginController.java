package com.swpym.blog.controller;

import com.swpym.blog.annotation.PassToken;
import com.swpym.blog.annotation.UserLoginToken;
import com.swpym.blog.api.model.BaseResponse;
import com.swpym.blog.api.model.LoginParam;
import com.swpym.blog.common.util.CookieUtil;
import com.swpym.blog.common.util.JWTUtil;
import com.swpym.blog.constant.UserSessionConst;
import com.swpym.blog.pojo.User;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.swpym.blog.service.UserService;


/**
 * @description: 用户登录操作
 * @author: shaowei
 * @date: 2020-03-09 14:18
 */
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    private static final Logger _logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/login")
    @PassToken
    public BaseResponse<String> login(@RequestHeader(name = "Content-Type", defaultValue = "application/json") String contentType,
                                      @RequestBody LoginParam loginParam) {
        _logger.info("用户请求登录获取Token");
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();
        // 获取用户信息到数据库
        User user = userService.findAccountInfoByUsername(username);
        if (user == null) {
            throw new UnauthorizedException("用户不存在");
        }
        String encodedPassword = user.getPassword();
        if (password.equals(user.getPassword())) {
            // 生成token
            String token = JWTUtil.sign(username, encodedPassword);
            // 加入cookie
            CookieUtil.addCookie(UserSessionConst.TOKEN_COOKIE, token, UserSessionConst.EXPIRE_SECONDS);
            return new BaseResponse<>(true, "Login success", null);
        } else {
            throw new UnauthorizedException("密码错误");
        }
    }

    @PostMapping("/find")
    @UserLoginToken
    public User find() {
        return new User();
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    @UserLoginToken
    public void loginOut() {
        _logger.info("用户退出登录");
        CookieUtil.removeCookie(UserSessionConst.TOKEN_COOKIE);
    }

}
