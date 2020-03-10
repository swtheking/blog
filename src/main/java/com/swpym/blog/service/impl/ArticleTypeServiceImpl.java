package com.swpym.blog.service.impl;

import com.swpym.blog.dao.ArticleTypeDao;
import com.swpym.blog.service.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author panym
 * @Description: ${文章类型Service层实现类}
 * @date 9:36  2020/3/10
 */
@Service
public class ArticleTypeServiceImpl implements ArticleTypeService {
    @Autowired
    private ArticleTypeDao articleTypeDao;
}
