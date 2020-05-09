package com.swpym.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: shaowei
 * @date: 2020-04-27 14:30
 */
@Slf4j
@Component
@Configuration
public class TopicReceiver {

    @RabbitHandler
    @RabbitListener(queues = "topic1")
    public void topicReceiver1(String content){
        log.info("通配符模式1开始订阅：{}",content);
    }


    @RabbitHandler
    @RabbitListener(queues = "topic2")
    public void topicReceiver2(String content){
        log.info("通配符模式2开始订阅：{}",content);
    }

    @RabbitHandler
    @RabbitListener(queues = "topic3")
    public void topicReceiver3(String content){
        log.info("通配符模式3开始订阅：{}",content);
    }

    @RabbitHandler
    @RabbitListener(queues = "topic4")
    public void topicReceiver4(String content){
        log.info("通配符模式4开始订阅：{}",content);
    }
}
