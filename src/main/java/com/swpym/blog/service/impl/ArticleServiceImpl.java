package com.swpym.blog.service.impl;

import com.swpym.blog.dao.ArticleDao;
import com.swpym.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author panym
 * @Description: ${文章Service层实现类}
 * @date 9:33  2020/3/10
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
}
