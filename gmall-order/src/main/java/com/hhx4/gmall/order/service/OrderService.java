package com.hhx4.gmall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhx4.common.to.mq.SeckillOrderTo;
import com.hhx4.common.utils.PageUtils;
import com.hhx4.gmall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author hangXing
 * @email hhx4@gmail.com
 * @date 2021-06-03 22:01:00
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void createSeckillOrder(SeckillOrderTo seckillOrderTo);
}

