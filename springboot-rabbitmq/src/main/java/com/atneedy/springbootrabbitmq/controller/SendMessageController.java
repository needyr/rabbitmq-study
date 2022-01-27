package com.atneedy.springbootrabbitmq.controller;

import com.atneedy.springbootrabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Description 发送延迟消息
 * @Author wuhujian
 * @Date 2022/1/24 下午 2:57
 */

@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMessageController {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    //构造器注入方式
    public SendMessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 开始发消息
     *
     * @param message：发送的消息
     */
    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message) {

        log.info("当前时间：{},发送一条信息给两个TTL队列:{}", new Date(), message);

        rabbitTemplate.convertAndSend("x", "XA", "消息来自ttl为10s：" + message);
        rabbitTemplate.convertAndSend("x", "XB", "消息来自ttl为40s：" + message);
    }

    /**
     * @author wuhujian
     * @date 2022-01-27 上午 10:35
     * @Description 开始发消息（带ttl）
     */
    @GetMapping("/sendExpirationMsg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message, @PathVariable String ttlTime) {

        log.info("当前时间：{},发送一条时常是{}ms的TTL消息给他队列QC:{}", new Date(), ttlTime, message);

        rabbitTemplate.convertAndSend("x", "XC", "消息来自ttl为：" + ttlTime + "ms" + message, msg -> {
            //设置发送消息的延迟市场
            msg.getMessageProperties().setExpiration(ttlTime);

            return msg;
        });
    }

    /**
     * @author wuhujian
     * @date 2022-01-27 下午 2:10
     * @Description 开始发消息（基于延迟队列插件）
     */
    @GetMapping("/sendDelayedMsg/{message}/{delayTime}")
    public void sendMsg(@PathVariable String message, @PathVariable Integer delayTime) {

        log.info("当前时间：{},发送一条时常是{}ms的消息给延迟队列delayed.queue:{}", new Date(), delayTime, message);

        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME,
                DelayedQueueConfig.DELAYED_ROUTING_KEY,
                message,
                msg -> {
                    msg.getMessageProperties().setDelay(delayTime);
                    return msg;
                });
    }
}
