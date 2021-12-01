package com.hhx4.gmall.seckill.schedlued;

import com.hhx4.gmall.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 商品秒杀定时任务
 **/

@Slf4j
@Service
@EnableScheduling
public class SeckillSkuSchedlued {

    @Autowired
    private SeckillService seckillService;

    @Scheduled(cron = "0 * * * * ?")
    public void uploadLatest3Day() {
        //TODO 幂等性处理
        log.info("商品上架完成");
        seckillService.uploadSkuLatest3Day();
    }
}