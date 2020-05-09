package com.swpym.blog.dao;

import com.swpym.blog.pojo.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author panym
 * @Description: ${用户信息Dao层}
 * @date 9:51  2020/3/10
 */
@Repository
public interface UserInfoDao extends JpaRepository<UserInfo,Long> {

    @Query(value = "select new com.swpym.blog.pojo.UserInfo(u.username,u.password) from UserInfo u where u.username = :username")
    UserInfo findAccountInfoByUsername(String username);

    UserInfo findByUsername(String username);
}
