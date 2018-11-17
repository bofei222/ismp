package com.nei.ismp.controller;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * @Author bofei
 * @Date 2018/11/16 18:02
 * @Description
 */
@Component
@RabbitListener(queues = "hello")
public class Receiver {
    private final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @RabbitHandler
    public void process(Channel channel, String string, Message message) throws IOException {
        try {
            doWork(string);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    private void doWork(String task) throws InterruptedException {
        LOGGER.info("消费者1，消费{}", task);
        TimeUnit.SECONDS.sleep(1);
    }
}
