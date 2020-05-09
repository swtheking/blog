package com.swpym.blog.service;

import com.swpym.blog.pojo.Article;

/**
 * @author panym
 * @Description: ${文章Service层}
 * @date 9:33  2020/3/10
 */
public interface ArticleService {

    public void saveBlog(Article article, String username,Integer typeId,Integer subTypeId);

}
