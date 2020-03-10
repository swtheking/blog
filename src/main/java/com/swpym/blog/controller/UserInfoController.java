package com.swpym.blog.controller;

import com.swpym.blog.api.model.BaseResponse;
import com.swpym.blog.pojo.UserInfo;
import com.swpym.blog.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "用户信息Controller",tags = {"用户信息操作接口"})
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "获取所有用户信息")
    @GetMapping("/findAll")
    public List<UserInfo> findAll(){
        return userInfoService.findAll();
    }

    @ApiOperation(value = "新增用户信息")
    @PostMapping("/addUser")
    public BaseResponse addUser(@RequestBody UserInfo userInfo){
        userInfoService.addUser(userInfo);
        return new BaseResponse(true,"保存成功",null);
    }

    @ApiOperation(value = "根据用户登录账号获取用户账户信息")
    @PostMapping("/findAccount")
    public UserInfo findAccountInfoByUsername(@RequestBody String username){
        return userInfoService.findAccountInfoByUsername(username);
    }

}
