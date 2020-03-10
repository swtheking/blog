package com.swpym.blog.dao;

import com.swpym.blog.pojo.ArticleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author panym
 * @Description: ${文章类型Dao层}
 * @date 9:36  2020/3/10
 */
@Repository
public interface ArticleTypeDao extends JpaRepository<ArticleType,Long> {
}
