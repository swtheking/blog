package com.swpym.blog.dao;

import com.swpym.blog.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author panym
 * @Description: ${Jpa测试类}
 * @date 13:58  2020/3/9
 */
@Repository
public interface UserDao extends JpaRepository<User,Long> {

}
