package com.nei.ismp.controller;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Author bofei
 * @Date 2018/11/17 7:55
 * @Description
 */
@RestController
public class FileRabbitmq {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileRabbitmq.class);

    @Resource
    private AmqpTemplate rabbitTemplate;

    @GetMapping("/sendFile")
    public void sendFile() {
        for (int i = 0; i < 10; i++) {
            this.rabbitTemplate.convertAndSend("file_exchange", "file_routing_key", "要发送的file " + i);
        }
    }


    @RabbitListener(queues = "file_queue")
    public void processMessage(Channel channel, String content, Message message) throws IOException, InterruptedException {
        LOGGER.info("消费者1:{}", content);
        TimeUnit.SECONDS.sleep(1);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

    }

    @RabbitListener(queues = "file_queue")
    public void processMessage2(Channel channel, String content, Message message) throws IOException, InterruptedException {
        LOGGER.info("消费者2:{}", content);
        TimeUnit.SECONDS.sleep(3);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
