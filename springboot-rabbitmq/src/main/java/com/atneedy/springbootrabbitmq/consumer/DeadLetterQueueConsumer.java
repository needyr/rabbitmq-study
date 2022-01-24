package com.atneedy.springbootrabbitmq.consumer;



import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
* @Author Albert wu
* @Date 25/1/2022 0:10
* @Description 队列TTL 消费者
*/

@Slf4j
@Component
public class DeadLetterQueueConsumer {

    //接受消息

    @RabbitListener(queues = "QD")
    public void receiveD(Message message, Channel channel) {
        String msg = new String(message.getBody());
        log.info("当前时间:{},接收到私死信队列的消息:{}",new Date(),msg);
    }
}
