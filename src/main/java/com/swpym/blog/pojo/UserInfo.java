package com.swpym.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author panym
 * @Description: ${用户信息表}
 * @date 17:18  2020/3/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "user_info")
@Entity
public class UserInfo {

    @Id    //主键id
    @GeneratedValue(strategy= GenerationType.IDENTITY)//主键生成策略
    @Column(name = "id")//数据库字段名
    private Long id;

    @Column(name = "username", nullable = false, unique = true, columnDefinition = "varchar(100) comment '用户账号'")
    private String username;

    @Column(name = "password", nullable = false, columnDefinition = "varchar(100) comment '用户密码' " )
    private String password;

    @Column(name = "email", columnDefinition = "varchar(255) default '' comment '用户邮箱'")
    private String email;

    @Column(name = "intro", columnDefinition = "varchar(255) default '' comment '用户个人简介'")
    private String intro;

    @Column(name = "avatar_url", columnDefinition = "varchar(1000) default '' comment '用户头像链接地址'")
    private String avatarUrl;

    @Column(name = "nickname", nullable = false, columnDefinition = "varchar(255) comment '用户昵称'")
    private String nickname;

    @Column(name = "sex", columnDefinition = "tinyint(2) comment '用户性别1:男 0:女'")
    private Integer sex;

    @Column(name = "birth", columnDefinition = "date comment '出生日期'")
    private String birth;

    @Column(name = "create_time", columnDefinition = "datetime comment '创建日期'")
    private String createTime;

    @Column(name = "update_time", columnDefinition = "datetime comment '修改日期'")
    private String updateTime;

    @Column(name = "status", columnDefinition = "tinyint(3) comment '用户状态'")
    private Integer status;

    public UserInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", intro='" + intro + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", birth='" + birth + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", status=" + status +
                '}';
    }
}
