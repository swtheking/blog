package com.swpym.blog.controller;

import com.swpym.blog.annotation.PassToken;
import com.swpym.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.swpym.blog.pojo.User;

import java.util.List;

/**
 * @author panym
 * @Description: ${用户信息的控制层}
 * @Date 14:21  2020/3/9
 */

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/findAll")
    @PassToken
    public List<User> findAll(){
        return userService.findAllUsers();
    }

    @GetMapping(value = "/findAccount")
    @PassToken
    public User findAccount(@RequestParam(value = "username") String username){
        return userService.findAccountInfoByUsername(username);
    }

    @RequestMapping("/test")
    public void test(){
        userService.test();
    }
}
