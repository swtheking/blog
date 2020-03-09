package com.swpym.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @description:
 * @author: shaowei
 * @date: 2020-03-09 13:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "user")
@Entity
public class User {

    @Id	//主键id
    @GeneratedValue(strategy=GenerationType.IDENTITY)//主键生成策略
    @Column(name = "id")//数据库字段名
    int Id;
    @Column(name = "username")
    String username;
    @Column(name = "password")
    String password;
}
