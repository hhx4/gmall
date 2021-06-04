package com.hhx4.gmall.order.dao;

import com.hhx4.gmall.order.entity.OrderSettingEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单配置信息
 * 
 * @author hangXing
 * @email hhx4@gmail.com
 * @date 2021-06-03 22:01:00
 */
@Mapper
public interface OrderSettingDao extends BaseMapper<OrderSettingEntity> {
	
}
