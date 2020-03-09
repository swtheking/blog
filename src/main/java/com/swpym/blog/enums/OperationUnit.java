package com.swpym.blog.enums;

/**
 * @description: 日志相关枚举
 * @author: shaowei
 * @date: 2020-03-09 15:49
 */
public enum OperationUnit {

    /**
     * 被操作的单元
     */
    UNKNOWN("unknown"),
    USER("user"),
    EMPLOYEE("employee"),
    Redis("redis");

    private String value;

    OperationUnit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
