package com.hhx4.gmall.order.dao;

import com.hhx4.gmall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author hangXing
 * @email hhx4@gmail.com
 * @date 2021-06-03 22:01:00
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
