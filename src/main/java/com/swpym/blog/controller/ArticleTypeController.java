package com.swpym.blog.controller;

import com.swpym.blog.service.ArticleTypeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panym
 * @Description: ${文章类型控制层}
 * @date 9:39  2020/3/10
 */
@RestController
@RequestMapping("/articleType")
@Api
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;
}
