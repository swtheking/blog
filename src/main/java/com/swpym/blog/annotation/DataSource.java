package com.swpym.blog.annotation;

import com.swpym.blog.mutidatesource.DSEnum;

import java.lang.annotation.*;

/**
 * @description: 多数据源标识
 * @author: shaowei
 * @date: 2020-03-10 17:04:23
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DataSource {

    // 默认是主数据库
    String name() default DSEnum.DATA_SOURCE_CORE;
}
