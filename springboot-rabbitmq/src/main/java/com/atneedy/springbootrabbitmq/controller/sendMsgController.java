package com.atneedy.springbootrabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Description: 发送延迟消息
 * @Author: wuhujian
 * @Date: 2022/1/24 下午 2:57
 */

@Slf4j
@RestController
@RequestMapping("/ttl")
public class sendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //开始发消息
    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message){

        log.info("当前时间：{},发送一条信息给两个TTL队列:{}",new Date().toString(),message);

        rabbitTemplate.convertAndSend("X","XA","消息来自ttl为10s");
    }

}
