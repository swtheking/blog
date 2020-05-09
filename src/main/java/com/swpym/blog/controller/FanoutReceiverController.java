package com.swpym.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: shaowei
 * @date: 2020-04-27 13:50
 */
@RestController
@Slf4j
@RequestMapping("/FanoutSender")
public class FanoutReceiverController {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("/fanoutSender")
    public  void  fanout(String content){
        log.info("fanout开始广播数据：{}",content);
        this.amqpTemplate.convertAndSend("fanout.test","",content);
    }

    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("fanout1"),
            exchange = @Exchange("fanout.test")
    ))
    public void fanoutReceiver2(String content){
        log.info("广播模式1开始消费：{}",content);
    }

    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("fanout2"),
            exchange = @Exchange("fanout.test")
    ))
    public void fanoutReceiver1(String content){
        log.info("广播模式2开始消费：{}",content);
    }

}
