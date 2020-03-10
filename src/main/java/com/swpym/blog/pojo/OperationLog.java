package com.swpym.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @description:
 * @author: shaowei
 * @date: 2020-03-09 15:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "c_operation_log")
@Entity
public class OperationLog {

    @Id    //主键id
    @Column(name = "id", columnDefinition = "varchar(64)")//数据库字段名
    private String id;

    @Column(name = "create_time")
    private Date createTime;
    /**
     * 日志等级
     */
    @Column(name = "log_level")
    private Integer level;

    /**
     * 被操作的对象
     */
    @Column(name = "operation_unit")
    private String operationUnit;

    /**
     * 方法名
     */
    @Column(name = "method")
    private String method;

    /**
     * 参数
     */
    @Column(name = "args")
    private String args;

    /**
     * 操作人id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 操作人
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 日志描述
     */
    @Column(name = "log_describe")
    private String describe;

    /**
     * 操作类型
     */
    @Column(name = "operation_type")
    private String operationType;

    /**
     * 方法运行时间
     */
    @Column(name = "run_time")
    private Long runTime;

    /**
     * 方法返回值
     */
    @Column(name = "return_value")
    private String returnValue;
}
