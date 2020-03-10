package com.swpym.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author panym
 * @Description: ${文章的实体类}
 * @date 20:32  2020/3/9
 */

@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "article")
@Entity
public class Article {

    @Id    //主键id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    @Column(name = "id")//数据库字段名
    private Long id;

    @Column(name = "title", nullable = false, columnDefinition = "varchar(100) comment '标题'")
    private String title;

    @Column(name = "sub_title", columnDefinition = "varchar(100) comment '副标题'")
    private String subTitle;

    @Column(name = "brief", columnDefinition = "varchar(1000) comment '简介'")
    private String brief;

    @Column(name = "content", columnDefinition = "longtext comment '文章内容'")
    private String content;

    @Column(name = "visible", columnDefinition = "tinyint(4) comment '可见性'")
    private Integer visible;

}
