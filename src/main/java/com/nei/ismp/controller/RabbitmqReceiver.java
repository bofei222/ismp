package com.nei.ismp.controller;

import com.alibaba.fastjson.JSONObject;
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
 * @Date 2018/11/16 19:07
 * @Description
 */
@Component
@RabbitListener(queues = "ismp_queue")
public class RabbitmqReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitmqReceiver.class);

    @RabbitHandler
    public void process(Channel channel, JSONObject jo, Message message) throws IOException {

        try {
            doWork(jo.toJSONString());
        } finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }

    }

    private void doWork(String task)  {
        try {
            LOGGER.info("消费者2，消费{}", task);
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
