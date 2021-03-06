package com.swpym.blog.interceptor;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.swpym.blog.annotation.PassToken;
import com.swpym.blog.annotation.UserLoginToken;
import com.swpym.blog.common.util.CookieUtil;
import com.swpym.blog.common.util.JWTUtil;
import com.swpym.blog.constant.UserSessionConst;
import com.swpym.blog.pojo.UserInfo;
import com.swpym.blog.service.UserInfoService;
import com.swpym.blog.service.UserService;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import com.swpym.blog.pojo.User;

/**
 * @description: 拦截器 拦截token，并验证
 * @author: shaowei
 * @date: 2020-03-09 14:03
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = CookieUtil.getCookie(UserSessionConst.TOKEN_COOKIE);
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }else {
                return checkToken(token);
            }
        } else {
            return checkToken(token);
        }
    }

    /*
     * @description: token验证
     * @author: shaowei
     * @date: 2020-03-10 14:48:53
     * @param token
     * @return: boolean
     */
    private boolean checkToken(String token) {
        // 执行认证
        if (token == null) {
            throw new UnauthorizedException("无token，请重新登录");
        }
        // 获取 token 中的用户名
        String username = null;
        try {
            username = JWTUtil.getUsername(token);
        } catch (JWTDecodeException j) {
            throw new UnauthorizedException("token获取用户信息异常");
        }
        // 根据用户账号获取账号和密码信息
        UserInfo user = userInfoService.findAccountInfoByUsername(username);
        if (user == null) {
            // 一般是跳转到 登录界面
            throw new UnauthorizedException("用户不存在，请重新登录");
        }
        // 验证 token
        try {
            JWTUtil.verify(token, user.getUsername(), user.getPassword());
        } catch (JWTVerificationException e) {
            throw new RuntimeException("token验证失败");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
