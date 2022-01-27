package com.atneedy.springbootrabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 延迟队列配置文件
 * @Author: wuhujian
 * @Date: 2022-01-27 上午 11:47
 */

@Configuration
public class DelayedQueueConfig {
    //交换机
    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";
    //队列
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";
    //routingKey
    public static final String DELAYED_ROUTING_KEY = "delayed.routingKey";

    //声明交换机
    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");

        return new CustomExchange(DELAYED_EXCHANGE_NAME, "x-delayed-message", false, true, arguments);
    }

    //声明队列
    @Bean
    public Queue delayedQueue() {

        return new Queue(DELAYED_QUEUE_NAME);
    }

    //绑定队列
    @Bean
    public Binding delayedQueueBinding(@Qualifier("delayedQueue") Queue delayedQueue,
                                       @Qualifier("delayedExchange") CustomExchange delayedExchange) {

        return BindingBuilder
                .bind(delayedQueue)
                .to(delayedExchange)
                .with(DELAYED_ROUTING_KEY)
                .noargs();

    }
}
