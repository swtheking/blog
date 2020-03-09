package com.swpym.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author panym
 * @Description: ${todo}
 * @date 20:44  2020/3/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "article_type")
@Entity
public class ArticleType {

    @Id    //主键id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    @Column(name = "id")//数据库字段名
    private int id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(100) comment '类别名称'")
    private String name;

    @Column(name = "brief", columnDefinition = "varchar(1000) comment '类别简介'")
    private String brief;

    @Column(name = "level", columnDefinition = "tinyint(3) comment '类别级别 1:大类'")
    private int level;
}
