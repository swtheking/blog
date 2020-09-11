package com.swpym.blog.controller;

import com.swpym.blog.annotation.PassToken;
import com.swpym.blog.config.properties.AiOssProperties;
import com.swpym.blog.service.UserInfoService;
import com.swpym.blog.util.AliyunOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

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

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/materialUpload")
    @PassToken
    public String test(@RequestParam(name = "file") MultipartFile file) {
        return aliyunOssUtil.uploadObjectOSS(aiOssProperties.getBucketName(),
                aiOssProperties.getPath(), file);
    }

    @GetMapping("/testa")
    public void asyncTest() {
        for (int i = 0; i < 20; i++) {
            userInfoService.test(i);
        }

    }

    @GetMapping("/downloadFile")
    @PassToken
    public void downloadFile(String fileUrl, String fileName,
                             HttpServletResponse response) throws Exception{
        //通知浏览器以附件形式下载
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
        this.aliyunOssUtil.exportOssFile(response.getOutputStream(),aiOssProperties.getBucketName());
    }

}
