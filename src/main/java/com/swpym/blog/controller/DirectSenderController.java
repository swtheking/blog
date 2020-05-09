package com.swpym.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: shaowei
 * @date: 2020-04-27 18:07
 */
@Slf4j
@RestController
@RequestMapping("DirectSender")
public class DirectSenderController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("directSender")
    public void directSender(String content,String key){
        log.info("路由模式开始生产消息：{},key:{}",content,key);
        this.amqpTemplate.convertAndSend("direct.test",key,content);
    }
}
