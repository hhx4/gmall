package com.hhx4.gmall.seckill.to;

import com.hhx4.gmall.seckill.vo.SkuInfoVo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * redis存储的秒杀信息格式
 **/
@Data
public class SeckillSkuRedisTo {
    private Long promotionId;
    private Long promotionSessionId;
    private Long skuId;
    private BigDecimal seckillPrice;
    private BigDecimal seckillCount;
    private BigDecimal seckillLimit;
    private Integer seckillSort;
    private String randomCode;
    private SkuInfoVo skuInfo;
    private Long startTime;
    private Long endTime;
}