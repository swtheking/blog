package com.swpym.blog.controller;

import com.alibaba.fastjson.JSON;
import com.swpym.blog.annotation.PassToken;
import com.swpym.blog.annotation.UserLoginToken;
import com.swpym.blog.api.model.BaseResponse;
import com.swpym.blog.api.model.LoginParam;
import com.swpym.blog.common.util.CookieUtil;
import com.swpym.blog.common.util.JWTUtil;
import com.swpym.blog.constant.UserSessionConst;
import com.swpym.blog.pojo.User;
import com.swpym.blog.pojo.UserInfo;
import com.swpym.blog.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author panym
 * @Description: ${用户信息控制层}
 * @date 9:55  2020/3/10
 */
@RequestMapping("/userinfo")
@RestController
@Api(value = "用户信息Controller", tags = {"用户信息接口"})
@Slf4j
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "获取所有用户信息")
    @GetMapping("/findAll")
    public List<UserInfo> findAll() {
        return userInfoService.findAll();
    }

    @ApiOperation(value = "新增用户信息")
    @PostMapping("/addUser")
    @PassToken
    public BaseResponse addUser(@RequestBody UserInfo userInfo) {
        log.info(JSON.toJSONString(userInfo));
        userInfoService.addUser(userInfo);
        return BaseResponse.success(null);
    }

    @ApiOperation(value = "根据用户登录账号获取用户账户信息")
    @PostMapping("/findAccount")
    public UserInfo findAccountInfoByUsername(@RequestBody String username) {
        return userInfoService.findAccountInfoByUsername(username);
    }

    @PostMapping("/login")
    @PassToken
    public BaseResponse<String> login(@RequestHeader(name = "Content-Type", defaultValue = "application/json") String contentType,
                                      @RequestBody LoginParam loginParam) {
        log.info("用户请求登录获取Token");
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();
        // 获取用户信息到数据库
        UserInfo user = userInfoService.checkLogin(username, password);
        return BaseResponse.success("登录成功");
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    @UserLoginToken
    public BaseResponse<String> loginOut() {
        log.info("用户退出登录");
        CookieUtil.removeCookie(UserSessionConst.TOKEN_COOKIE);
        return BaseResponse.success("退出成功");
    }

    @ApiOperation(value = "根据用户账号信息获取用户")
    @GetMapping(value = "/findUserInfoByUsername")
    public BaseResponse<UserInfo> findUserInfoByUsername(String username){
        UserInfo userInfo = userInfoService.findUserInfoByUsername(username);
        return BaseResponse.success(userInfo);
    }

}
