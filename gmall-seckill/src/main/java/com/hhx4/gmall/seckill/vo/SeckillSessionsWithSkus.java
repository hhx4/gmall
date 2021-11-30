package com.hhx4.gmall.seckill.vo;


import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 秒杀活动信息vo
 **/
@Data
public class SeckillSessionsWithSkus {
    private Long id;
    /**
     *
     */
    private String name;
    /**
     * ÿ
     */
    private Date startTime;
    /**
     * ÿ
     */
    private Date endTime;
    /**
     *
     */
    private Integer status;
    /**
     *
     */
    private Date createTime;
    List<SeckillSkuVo> relationSkus;
}