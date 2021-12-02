package com.hhx4.gmall.seckill.service;

/*
* 定时秒杀业务
 */

import com.hhx4.gmall.seckill.to.SeckillSkuRedisTo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeckillService {

    void uploadSkuLatest3Day();

    List<SeckillSkuRedisTo> getCurrentSeckillSkus();
}
