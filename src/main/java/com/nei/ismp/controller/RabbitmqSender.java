package com.nei.ismp.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author bofei
 * @Date 2018/11/15 19:40
 * @Description
 */
@RestController
public class RabbitmqSender {
    private final static Logger LOGGER = LoggerFactory.getLogger(RabbitmqSender.class);
    @Resource
    private AmqpTemplate rabbitTemplate;

    @GetMapping("/sendHello")
    public void sayHello() {
        for (int i = 0; i < 100; i++) {
            this.rabbitTemplate.convertAndSend("hello", String.valueOf(i));

        }
    }

    @GetMapping("/send")
    public void send() {
        JSONObject jo = new JSONObject();
        jo.put("ID", "1");
        jo.put("NAME", "BOFEI");
        this.rabbitTemplate.convertAndSend("ismp_exchange", "ismp_routing_key", jo);
    }




}
