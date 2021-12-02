package com.hhx4.gmall.seckill.schedlued;

import com.hhx4.gmall.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 商品秒杀定时任务
 **/

@Slf4j
@Service
@EnableScheduling
public class SeckillSkuSchedlued {

    @Autowired
    private SeckillService seckillService;
    @Autowired
    private RedissonClient redissonClient;

    private final String UPLOAD_LOCK = "seckill:upload:lock";

    @Scheduled(cron = "0 * * * * ?")
    public void uploadLatest3Day() {
        RLock lock = redissonClient.getLock(UPLOAD_LOCK);
        //加一个分布式的锁
        lock.lock(10, TimeUnit.SECONDS);
        try {
            log.info("商品上架完成");
            seckillService.uploadSkuLatest3Day();
        } finally {
            lock.unlock();
        }

    }
}