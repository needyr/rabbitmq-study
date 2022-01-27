package com.atneedy.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 发布确认高级
 * @Author: wuhujian
 * @Date: 2022-01-27 下午 5:00
 */

@Configuration
public class ConfirmConfig {
    //交换机
    public static final String CONFIRM_EXCHANGE_NAME = "confirm.exchange";
    //队列
    public static final String CONFIRM_QUEUE_NAME = "confirm.queue";
    //routingKey
    public static final String CONFIRM_ROUTING_KEY = "key1";

    //交换机声明
    @Bean("confirmExchange")
    public DirectExchange confirmExchange() {
        return new DirectExchange(CONFIRM_EXCHANGE_NAME);
    }

    //队列声明
    @Bean("confirmQueue")
    public Queue confirmQueue() {
        return QueueBuilder.nonDurable(CONFIRM_QUEUE_NAME).build();
    }

    //绑定
    @Bean
    public Binding confirmQueueBinding(@Qualifier("confirmExchange") DirectExchange confirmExchange,
                                       @Qualifier("confirmQueue") Queue confirmQueue) {

        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_ROUTING_KEY);
    }
}
