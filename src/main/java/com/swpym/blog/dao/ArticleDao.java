package com.swpym.blog.dao;

import com.swpym.blog.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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

    @Modifying
    @Query(value = "insert into r_article_user_type (article_id,user_id,type_id,sub_type_id) values (?1,?2,?3,?4)",nativeQuery = true)
    void saveRelationToUser(Long articleId,Long userId,Integer typeId,Integer subTypeId);
}
