package com.hhx4.gmall.seckill.controller;

import com.hhx4.common.utils.R;
import com.hhx4.gmall.seckill.service.SeckillService;
import com.hhx4.gmall.seckill.to.SeckillSkuRedisTo;
import com.hhx4.gmall.seckill.vo.SkuInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 秒杀服务
 **/
@RestController
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    @GetMapping("/seckill/skus")
    public R getSeckillSkus(){
        List<SeckillSkuRedisTo> list = seckillService.getCurrentSeckillSkus();
        return R.ok().setData(list);
    }

    @GetMapping("/sku/seckill/{skuId}")
    public R getSkuSeckillInfo(@PathVariable("skuId") Long skuId){
        SeckillSkuRedisTo to = seckillService.getSkuSeckillInfo(skuId);
        return R.ok().setData(to);
    }

    @GetMapping("/kill")
    public R seckill(@RequestParam("killId")String killId,
                     @RequestParam("key")String key,
                     @RequestParam("num") Integer num){

        String orderSn = seckillService.kill(killId,key,num);

        return R.ok().setData(orderSn);
    }

}