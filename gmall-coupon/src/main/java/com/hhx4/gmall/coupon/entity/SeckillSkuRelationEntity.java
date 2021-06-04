package com.hhx4.gmall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author hangXing
 * @email hhx4@gmail.com
 * @date 2021-06-03 21:47:32
 */
@Data
@TableName("sms_seckill_sku_relation")
public class SeckillSkuRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long promotionId;
	/**
	 * 
	 */
	private Long promotionSessionId;
	/**
	 * 
	 */
	private Long skuId;
	/**
	 * 
	 */
	private BigDecimal seckillPrice;
	/**
	 * 
	 */
	private BigDecimal seckillCount;
	/**
	 * ÿ
	 */
	private BigDecimal seckillLimit;
	/**
	 * 
	 */
	private Integer seckillSort;

}
