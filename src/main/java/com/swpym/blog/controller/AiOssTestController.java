package com.swpym.blog.controller;

import com.swpym.blog.config.properties.AiOssProperties;
import com.swpym.blog.util.AliyunOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description:
 * @author: shaowei
 * @date: 2020-05-09 15:43
 */
@RestController
@RequestMapping("/test")
public class AiOssTestController {

    @Autowired
    private AliyunOssUtil aliyunOssUtil;

    @Autowired
    private AiOssProperties aiOssProperties;

    @PostMapping("/materialUpload")
    public String test(@RequestParam(name = "file") MultipartFile file) {
        return aliyunOssUtil.uploadObjectOSS(aiOssProperties.getBucketName(),
                aiOssProperties.getPath(), file);
    }

}
