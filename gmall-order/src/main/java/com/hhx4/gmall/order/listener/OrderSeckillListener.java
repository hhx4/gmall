package com.hhx4.gmall.order.listener;

import com.hhx4.common.to.mq.SeckillOrderTo;
import com.hhx4.gmall.order.service.OrderService;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 订单消息listener
 **/

@RabbitListener(queues = "order.seckill.order.queue")
@Component
@Slf4j
public class OrderSeckillListener {
    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void Listener(SeckillOrderTo seckillOrderTo, Channel channel, Message message) throws IOException {
      log.info("准备创建秒杀订单的详细信息。。。");
      orderService.createSeckillOrder(seckillOrderTo);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }

    }
}