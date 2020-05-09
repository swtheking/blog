package com.swpym.blog.controller;

import com.swpym.blog.annotation.PassToken;
import com.swpym.blog.api.model.BaseResponse;
import com.swpym.blog.pojo.Article;
import com.swpym.blog.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "用户新增博客")
    @PostMapping(value = "/saveBlog")
    @PassToken
    public BaseResponse<String> saveBlog(  @RequestBody Article article,
                                           @RequestParam String username,
                                           @RequestParam(required = false) Integer typeId,
                                           @RequestParam(required = false) Integer subTypeId){
        articleService.saveBlog(article,username,typeId,subTypeId);
        return BaseResponse.success("保存成功");
    }
}
