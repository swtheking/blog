package com.swpym.blog.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: shaowei
 * @date: 2020-04-27 14:27
 */
@Configuration
public class RabbitMqConfig {


    @Bean
    public Queue topic1() {
        return QueueBuilder.durable("topic1").build();
    }

    @Bean
    public Queue topic2() {
        return new Queue("topic2");
    }

    @Bean
    public Queue topic3() {
        return new Queue("topic3");
    }

    @Bean
    public Queue topic4() {
        return new Queue("topic4");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topic.test");
    }

    @Bean
    public Binding binding1(@Qualifier("topic1") Queue queue,
                            @Qualifier("topicExchange") TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("a.#");
    }

    @Bean
    public Binding binding2(@Qualifier("topic2") Queue queue,
                            @Qualifier("topicExchange") TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("a.*");
    }

    @Bean
    public Binding binding3(@Qualifier("topic3") Queue queue,
                            @Qualifier("topicExchange") TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("*.a");
    }

    @Bean
    public Binding binding4(@Qualifier("topic4") Queue queue,
                            @Qualifier("topicExchange") TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("#.a");

    }
}

