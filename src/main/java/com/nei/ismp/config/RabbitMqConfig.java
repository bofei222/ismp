package com.nei.ismp.config;


import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




/**
 * @Author bofei
 * @Date 2018/11/15 19:39
 * @Description
 */
@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }



    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);

        DirectExchange ismp_exchange = new DirectExchange("ismp_exchange");
        Queue ismp_queue = new Queue("ismp_queue",true,false, false);
//
        rabbitAdmin.declareQueue(ismp_queue);
        rabbitAdmin.declareExchange(ismp_exchange);
        rabbitAdmin.declareBinding(BindingBuilder.bind(ismp_queue).to(ismp_exchange).with("ismp_routing_key"));


        DirectExchange file_exchange = new DirectExchange("file_exchange");
        Queue file_queue = new Queue("file_queue",true,false, false);
//
        rabbitAdmin.declareQueue(file_queue);
        rabbitAdmin.declareExchange(file_exchange);
        rabbitAdmin.declareBinding(BindingBuilder.bind(file_queue).to(file_exchange).with("file_routing_key"));


        return rabbitAdmin;
    }


}
