package com.hhx4.gmall.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hhx4.common.utils.R;
import com.hhx4.gmall.seckill.feign.CouponFeignService;
import com.hhx4.gmall.seckill.service.SeckillService;
import com.hhx4.gmall.seckill.to.SeckillSkuRedisTo;
import com.hhx4.gmall.seckill.vo.SeckillSessionsWithSkus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品秒杀实现
 **/

@Service("SeckillService")
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    CouponFeignService couponFeignService;

    @Autowired
    StringRedisTemplate redisTemplate;

    private final String SESSIONS_CACHE_PREFIX = "seckill:sessions";
    private final String SECKILLS_CACHE_PREFIX = "seckill:sessions";



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
                SeckillSkuRedisTo redisTo = new SeckillSkuRedisTo();

                BeanUtils.copyProperties(item,redisTo);
                ops.put(item.getId(),item);
            });
        });
    }

    private void saveSessionInfo(List<SeckillSessionsWithSkus> data) {
        data.forEach(session->{
            long start_time = session.getStartTime().getTime();
            long end_time = session.getEndTime().getTime();
            String key = SESSIONS_CACHE_PREFIX +start_time+"_"+end_time;
            List<String> collect = session.getRelationSkus().stream().map(item->item.getId().toString()).collect(Collectors.toList());
            redisTemplate.opsForList().leftPushAll(key,collect);
        });
    }
}