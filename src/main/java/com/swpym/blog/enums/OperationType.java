package com.swpym.blog.enums;

/**
 * @description: 日志相关枚举
 * @author: shaowei
 * @date: 2020-03-09 15:48
 */
public enum OperationType {

    /**
     * 操作类型
     */
    UNKNOWN("unknown"),
    DELETE("delete"),
    SELECT("select"),
    UPDATE("update"),
    INSERT("insert");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    OperationType(String s) {
        this.value = s;
    }
}
