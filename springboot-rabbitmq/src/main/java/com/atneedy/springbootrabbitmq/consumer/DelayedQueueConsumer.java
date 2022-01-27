package com.atneedy.springbootrabbitmq.consumer;

import com.atneedy.springbootrabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description: 延迟队列(基于插件的)消费者
 * @Author: wuhujian
 * @Date: 2022-01-27 下午 2:17
 */

@Component
@Slf4j
public class DelayedQueueConsumer {

    //监听消息
    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    public void receiveDelayedMessage(Message message){
        String msg =new String(message.getBody());
        log.info("当前时间{},收到延迟队列的消息是:{}",new Date(),msg);
    }
}
