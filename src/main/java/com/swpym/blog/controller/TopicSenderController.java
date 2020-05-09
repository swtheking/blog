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
 * @date: 2020-04-27 14:29
 */
@Slf4j
@RestController
@RequestMapping("/TopicSender")
public class TopicSenderController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("/topicSender")
    public void toipc(String content, String key) {
        log.info("通配符模式开始发布消息:{},key:{}", content, key);
        this.amqpTemplate.convertAndSend("topic.test", key, content);
    }
}
