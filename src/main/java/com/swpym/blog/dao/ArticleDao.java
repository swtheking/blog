package com.swpym.blog.dao;

import com.swpym.blog.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author panym
 * @Description: ${文章信息Dao层}
 * @date 9:34  2020/3/10
 */
@Repository
public interface ArticleDao extends JpaRepository<Article,Long> {
    @Override
    List<Article> findAll();
}
