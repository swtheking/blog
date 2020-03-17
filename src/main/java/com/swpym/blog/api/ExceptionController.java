package com.swpym.blog.api;

import com.swpym.blog.api.model.BaseResponse;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 全局异常捕捉
 * @author: shaowei
 * @date: 2020-03-09 13:40
 */
@RestControllerAdvice
public class ExceptionController {

    // 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public BaseResponse<String> handle401(Throwable ex) {
        return BaseResponse.error(HttpStatus.UNAUTHORIZED.value(), "UnauthorizedException(token认证异常)", ex.getMessage());
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse globalException(HttpServletRequest request, Throwable ex) {
        return BaseResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "其他异常", ex.getMessage());
    }

    /*
     * @description: 获取请求的状态类型
     * @author: shaowei
     * @date: 2020-03-09 13:43:14
     * @param request
     * @return: org.springframework.http.HttpStatus
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }


}
