package com.swpym.blog.controller;

import com.swpym.blog.service.ArticleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panym
 * @Description: ${文章控制层}
 * @date 9:37  2020/3/10
 */
@RestController
@RequestMapping("/article")
@Api
public class ArticleController {
    @Autowired
    private ArticleService articleService;
}
