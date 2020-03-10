package com.swpym.blog.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class CookieUtil {

    /*
     * @description: 将token放入cookie中
     * @author: shaowei
     * @date: 2020-03-10 13:35:38
     * @param name
     * @param value
     * @param maxAge
     * @return: void
     */
    public static void addCookie(String name, String value, int maxAge) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    /*
     * @description: 获取当前请求中的token值
     * @author: shaowei
     * @date: 2020-03-10 13:35:58
     * @param name
     * @return: java.lang.String
     */
    public static String getCookie(String name) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        Cookie currentCookie = Arrays.stream(cookies)
                .filter(cookie -> name.equals(cookie.getName()))
                .findAny().orElse(null);
        if (currentCookie == null) {
            return null;
        }
        return currentCookie.getValue();
    }

    /*
     * @description: 去除当前请求中token
     * @author: shaowei
     * @date: 2020-03-10 13:36:23
     * @param name
     * @return: void
     */
    public static void removeCookie(String name) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        Cookie cookie = new Cookie(name, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
