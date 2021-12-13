package com.hhx4.gmall.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.hhx4.common.to.mq.SeckillOrderTo;
import com.hhx4.common.utils.R;
import com.hhx4.common.vo.MemberRespVo;
import com.hhx4.gmall.seckill.feign.CouponFeignService;
import com.hhx4.gmall.seckill.feign.ProductFeignService;
import com.hhx4.gmall.seckill.interceptor.LoginUserInterceptor;
import com.hhx4.gmall.seckill.service.SeckillService;
import com.hhx4.gmall.seckill.to.SeckillSkuRedisTo;
import com.hhx4.gmall.seckill.vo.SeckillSessionsWithSkus;
import com.hhx4.gmall.seckill.vo.SkuInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 商品秒杀实现
 **/

@Slf4j
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
    @Autowired
    RabbitTemplate rabbitTemplate;

    private final String SESSIONS_CACHE_PREFIX = "seckill:sessions:";
    private final String SECKILL_CACHE_PREFIX = "seckill:skus:";
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
            BoundHashOperations<String,Object,Object> ops = redisTemplate.boundHashOps(SECKILL_CACHE_PREFIX);
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
            List<String> collect = session.getRelationSkus().stream()
                    .map(item->item.getId().toString()).collect(Collectors.toList());
            if (!redisTemplate.hasKey(key)) {
                redisTemplate.opsForList().leftPushAll(key,collect);
            }
        });
    }
    @Override
    public List<SeckillSkuRedisTo> getCurrentSeckillSkus() {
        //获取当前时间
        long time = new Date().getTime();

        try{//Map.Entry entry = SphU.entry("seckillSkus")
            Set<String> keys = redisTemplate.keys(SESSIONS_CACHE_PREFIX + "*");
            for (String key : keys) {
                //seckill:sessions:1582250400000_1582254000000
                String replace = key.replace(SESSIONS_CACHE_PREFIX, "");
                String[] s = replace.split("_");
                Long start = Long.parseLong(s[0]);
                Long end = Long.parseLong(s[1]);
                if (time >= start && time <= end) {
                    //2、获取这个秒杀场次需要的所有商品信息
                    List<String> range = redisTemplate.opsForList().range(key, -100, 100);
                    BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SECKILL_CACHE_PREFIX);
                    List<String> list = hashOps.multiGet(range);
                    if (list != null) {
                        List<SeckillSkuRedisTo> collect = list.stream().map(item -> {
                            SeckillSkuRedisTo redis = JSON.parseObject((String) item, SeckillSkuRedisTo.class);
//                        redis.setRandomCode(null); 当前秒杀开始就需要随机码
                            return redis;
                        }).collect(Collectors.toList());
                        return collect;
                    }
                    break;
                }
            }
        }catch (Exception e){
            log.error("资源被限流,{}",e.getMessage());
        }

        return null;
    }
    
    @Override
    public SeckillSkuRedisTo getSkuSeckillInfo(Long skuId) {

        //1、找到所有需要参与秒杀的商品的key
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SECKILL_CACHE_PREFIX);


        Set<String> keys = hashOps.keys();
        if (keys != null && keys.size() > 0) {
            String regx = "\\d_" + skuId;
            for (String key : keys) {
                //6_4
                if (Pattern.matches(regx, key)) {
                    String json = hashOps.get(key);
                    SeckillSkuRedisTo skuRedisTo = JSON.parseObject(json, SeckillSkuRedisTo.class);
                    //TODO 加入非空判断
                    if (skuRedisTo == null) return null;
                    //随机码
                    long current = new Date().getTime();
                    if (current >= skuRedisTo.getStartTime() && current <= skuRedisTo.getEndTime()) {
                        //TODO
                    } else {
                        //TODO 当前商品已经过了秒杀时间要直接删除
                        hashOps.delete(key);
                        skuRedisTo.setRandomCode(null);
                    }
                    return skuRedisTo;
                }
                ;
            }
        }


        return null;
    }

    @Override
    public String kill(String killId, String key, Integer num) {

        MemberRespVo memberRespVo = LoginUserInterceptor.LoginUser.get();

        //1、获取商品详细信息
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SECKILL_CACHE_PREFIX);
        String json = hashOps.get(killId);
        if(StringUtils.isEmpty(json)){
            return null;
        }
        SeckillSkuRedisTo redis = JSON.parseObject(json, SeckillSkuRedisTo.class);
        //效验信息
        Long startTime = redis.getStartTime();
        Long endTime = redis.getEndTime();
        long now = new Date().getTime();
        long ttl = endTime - now;
        //校验时间
        if (now >= startTime && now <= endTime) {
            //效验随机码
            String randomCode = redis.getRandomCode();
            String skuId = redis.getPromotionSessionId()+"_"+redis.getSkuId();
            if (randomCode.equals(key) && killId.equals(skuId)) {
                //3、购物数量是否合理
                if(num <= redis.getSeckillLimit()){
                    //4、验证这个人是否已经买过了，秒杀成功，去redis中占一个
                    String redisKey = memberRespVo.getId()+"_"+skuId;
                    Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(redisKey, num.toString(), ttl, TimeUnit.MILLISECONDS);
                    //占位是否成功，是否买过
                    if(aBoolean){
                        //进行分布式信号量操作模拟减库存
                        RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + randomCode);
                        try {
                            boolean b = semaphore.tryAcquire(num, 100, TimeUnit.MILLISECONDS);
                            //秒杀成功，准备快速下单发送MQ消息
                            if(b){
                                String timeId = IdWorker.getTimeId();
                                SeckillOrderTo to = new SeckillOrderTo();
                                to.setOrderSn(timeId);
                                to.setMemberId(memberRespVo.getId());
                                to.setNum(num);
                                to.setPromotionSessionId(redis.getPromotionSessionId());
                                to.setSkuId(redis.getSkuId());
                                to.setSeckillPrice(redis.getSeckillPrice());
                                rabbitTemplate.convertAndSend("order-event-exchange","order.seckill.order",to);
                                return timeId;
                            }
                            return null;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }else {
            return null;
        }
        return null;
    }
}
