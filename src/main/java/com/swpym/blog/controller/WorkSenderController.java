package com.swpym.blog.controller;

import com.rabbitmq.client.AMQP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @description:
 * @author: shaowei
 * @date: 2020-04-27 13:45
 */
@RestController
@Slf4j
@RequestMapping("/WorkSender")
//放在类上，监听到消息后会交给@RabbitHandler的方法进行处理，如果有多个方法,会根据参数类型进行选择
//@RabbitListener(queuesToDeclare = @Queue("work") )
public class WorkSenderController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("/workSender")
    public void workSender(String content){
        log.info("work模式发送消息：{}",content);
        this.amqpTemplate.convertAndSend("work",content);
    }

    @RabbitListener(queuesToDeclare = @Queue("work"))
    @RabbitHandler
    public void workReceiver1(String content) throws InterruptedException {
        log.info("work模式开始消费1：{}",content);
        Thread.sleep(1000);

    }

    @RabbitListener(queuesToDeclare = @Queue("work") )
    @RabbitHandler
    public void workReceiver2(String content, AMQP.Channel channel) throws IOException, InterruptedException {
        log.info("work模式开始消费2：{}",content);
        Thread.sleep(1000);

    }
}
