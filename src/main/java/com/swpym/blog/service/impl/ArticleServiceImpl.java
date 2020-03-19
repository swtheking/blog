package com.swpym.blog.service.impl;

import com.swpym.blog.dao.ArticleDao;
import com.swpym.blog.dao.UserInfoDao;
import com.swpym.blog.pojo.Article;
import com.swpym.blog.pojo.UserInfo;
import com.swpym.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author panym
 * @Description: ${文章Service层实现类}
 * @date 9:33  2020/3/10
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    @Transactional
    public void saveBlog(Article article, String username,Integer typeId,Integer subTypeId) {
        UserInfo userInfo = userInfoDao.findByUsername(username);// 获取用户信息
        Article savedArticle = articleDao.save(article); // 保存文章
        // 保存信息到关联表
        articleDao.saveRelationToUser(savedArticle.getId(),userInfo.getId(),typeId,subTypeId);
    }
}
