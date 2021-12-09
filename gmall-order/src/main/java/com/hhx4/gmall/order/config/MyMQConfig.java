package com.hhx4.gmall.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 队列绑定关系
 **/
@Configuration
public class MyMQConfig {

    @Bean
    public Queue orderSeckillOrderQueue(){
        return new Queue("order.seckill.order.queue",true,false,false);
    }

    @Bean
    public Binding orderSeckillOrderBinding(){
        return new Binding("order.seckill.order.queue",Binding.DestinationType.QUEUE,"order-event-exchange"
        ,"order.seckill.order",null);
    }
}