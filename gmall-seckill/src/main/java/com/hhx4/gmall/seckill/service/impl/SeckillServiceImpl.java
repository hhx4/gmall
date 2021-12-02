package com.hhx4.gmall.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hhx4.common.utils.R;
import com.hhx4.gmall.seckill.feign.CouponFeignService;
import com.hhx4.gmall.seckill.feign.ProductFeignService;
import com.hhx4.gmall.seckill.service.SeckillService;
import com.hhx4.gmall.seckill.to.SeckillSkuRedisTo;
import com.hhx4.gmall.seckill.vo.SeckillSessionsWithSkus;
import com.hhx4.gmall.seckill.vo.SkuInfoVo;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品秒杀实现
 **/

@Service("SeckillService")
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    CouponFeignService couponFeignService;
    @Autowired
    ProductFeignService productFeignService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedissonClient redissonClient;

    private final String SESSIONS_CACHE_PREFIX = "seckill:sessions:";
    private final String SECKILLS_CACHE_PREFIX = "seckill:skus:";
    private final String SKU_STOCK_SEMAPHORE = "seckill:stock:";


    @Override
    public void uploadSkuLatest3Day() {
        //扫描最近三天需要上架的商品
        R session = couponFeignService.getLatest3DaySession();
        if (session.getCode() == 0) {
            //上架商品
            List<SeckillSessionsWithSkus> data = session.getData(new TypeReference<List<SeckillSessionsWithSkus>>() {
            });
            //保存秒杀活动信息到缓存
            saveSessionInfo(data);
            //缓存秒杀关联商品的信息
            saveSkuInfo(data);
        }
    }


    private void saveSkuInfo(List<SeckillSessionsWithSkus> data) {
        data.forEach(session->{
            BoundHashOperations<String,Object,Object> ops = redisTemplate.boundHashOps(SECKILLS_CACHE_PREFIX);
            session.getRelationSkus().forEach(item->{
                //存储秒杀随机码信息
                String code = UUID.randomUUID().toString().replace("-","");
                SeckillSkuRedisTo redisTo = new SeckillSkuRedisTo();

                if (!ops.hasKey(item.getPromotionSessionId().toString()+"_"+item.getSkuId())) {
                    //存储基本商品信息
                    R r = productFeignService.getSkuinfo(item.getSkuId());
                    SkuInfoVo skuInfo = r.getData("skuInfo", new TypeReference<SkuInfoVo>() {
                    });
                    redisTo.setSkuInfo(skuInfo);
//                //存储秒杀活动信息
                    BeanUtils.copyProperties(item,redisTo);
                    //存储秒杀时间信息
                    redisTo.setStartTime(session.getStartTime().getTime());
                    redisTo.setEndTime(session.getEndTime().getTime());
                    redisTo.setRandomCode(code);
                    String s = JSON.toJSONString(redisTo);
                    ops.put(item.getPromotionSessionId().toString()+"_"+item.getSkuId().toString(),s);
                    //分布式的信号量来控制库存；
                    RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + code);
                    semaphore.trySetPermits(item.getSeckillCount());
                }
            });
        });
    }

    private void saveSessionInfo(List<SeckillSessionsWithSkus> data) {
        data.forEach(session->{
            long start_time = session.getStartTime().getTime();
            long end_time = session.getEndTime().getTime();
            String key = SESSIONS_CACHE_PREFIX +start_time+"_"+end_time;
            List<String> collect = session.getRelationSkus().stream().map(item->item.getId().toString()).collect(Collectors.toList());
            if (!redisTemplate.hasKey(key)) {
                redisTemplate.opsForList().leftPushAll(key,collect);
            }
        });
    }
    @Override
    public List<SeckillSkuRedisTo> getCurrentSeckillSkus() {
        //获取当前时间
        long time = new Date().getTime();
        //获取当前秒杀活动
        Set<String> keys = redisTemplate.keys(SESSIONS_CACHE_PREFIX + "*");
        if (keys != null) {
            //todo 处理获取到的活动信息keys
        }
        //获取当前秒杀商品信息

        return null;
    }
}
