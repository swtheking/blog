package com.swpym.blog.common.vo;

/**
 * @description:
 * @author: shaowei
 * @date: 2020-03-20 16:23
 */
public class QRParamsException extends Exception {
    private static final long serialVersionUID = 834041053487274203L;
    public QRParamsException()  {}                //用来创建无参数对象
    public QRParamsException(String message) {        //用来创建指定参数对象
        super(message);                             //调用超类构造器
    }
}
