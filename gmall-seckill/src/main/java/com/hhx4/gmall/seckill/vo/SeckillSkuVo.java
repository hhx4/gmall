package com.hhx4.gmall.seckill.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 秒杀商品信息vo
 **/
@Data
public class SeckillSkuVo {
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