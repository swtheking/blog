package com.swpym.blog.dao;

import com.swpym.blog.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author panym
 * @Description: ${Jpa测试类}
 * @date 13:58  2020/3/9
 */
@Repository
public interface UserDao extends JpaRepository<User,Long> {

    @Query(value = "select new com.swpym.blog.pojo.User( u.username,u.password) from User u where u.username = :username")
    User findAccountInfoByUsername(@Param("username") String username);
}
