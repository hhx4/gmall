package com.hhx4.gmall.seckill.service;

/*
* 定时秒杀业务
 */

import org.springframework.stereotype.Service;

@Service
public interface SeckillService {

    void uploadSkuLatest3Day();
}
