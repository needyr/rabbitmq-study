package com.atneedy.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Description: 延迟队列配置文件
 * @Author: wuhujian
 * @Date: 2022/1/18 上午 10:13
 */

@Configuration
public class TtlQueueConfig {
    //普通交换机名称
    public static final String NORMAL_EXCHANGE = "x";
    //死信交换机名称
    public static final String DEAD_EXCHANGE = "y";
    //普通队列名称
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    //通用队列名称
    public static final String QUEUE_C = "QC";
    //死信队列名称
    public static final String DEAD_QUEUE = "QD";


    //声明普通交换机 bean里面的是别名 注入的时候用
    @Bean("normalExchange")
    public DirectExchange normalExchange() {
        return new DirectExchange(NORMAL_EXCHANGE);
    }

    //声明死信交换机
    @Bean("deadExchange")
    public DirectExchange deadExchange() {
        return new DirectExchange(DEAD_EXCHANGE);
    }

    //声明队列
    @Bean("queueA")
    public Queue queueA() {
        return QueueBuilder
                .nonDurable(QUEUE_A)
                .autoDelete()
                .ttl(10 * 1000)
                .deadLetterExchange(DEAD_EXCHANGE)
                .deadLetterRoutingKey("YD")
                .build();
    }

    //声明队列
    @Bean("queueB")
    public Queue queueB() {
        return QueueBuilder
                .nonDurable(QUEUE_B)
                .autoDelete()
                .ttl(40 * 1000)
                .deadLetterExchange(DEAD_EXCHANGE)
                .deadLetterRoutingKey("YD")
                .build();
    }

    //声明队列C
    @Bean("queueC")
    public Queue queueC() {
        return QueueBuilder
                .nonDurable(QUEUE_C)
                .autoDelete()
                .deadLetterExchange(DEAD_EXCHANGE)
                .deadLetterRoutingKey("YD")
                .build();
    }

    //声明死信队列
    @Bean("deadQueue")
    public Queue deadQueue() {
        return QueueBuilder
                .nonDurable(DEAD_QUEUE)
                .build();
    }

    /**
     * 交换机绑定队列
     */
    @Bean
    public Binding queueABinding(@Qualifier("queueA") Queue queueA,
                                 @Qualifier("normalExchange") DirectExchange normalExchange) {

        return BindingBuilder
                .bind(queueA).to(normalExchange).with("XA");
    }

    @Bean
    public Binding queueBBinding(@Qualifier("queueB") Queue queueB,
                                 @Qualifier("normalExchange") DirectExchange normalExchange) {

        return BindingBuilder
                .bind(queueB).to(normalExchange).with("XB");
    }

    @Bean
    public Binding queueCBinding(@Qualifier("queueC") Queue queueC,
                                 @Qualifier("normalExchange") DirectExchange normalExchange){

        return BindingBuilder
                .bind(queueC).to(normalExchange).with("XC");
    }

    @Bean
    public Binding deadQueueBinding(@Qualifier("deadQueue") Queue deadQueue,
                                    @Qualifier("deadExchange") DirectExchange deadExchange) {

        return BindingBuilder
                .bind(deadQueue).to(deadExchange).with("YD");
    }
}
