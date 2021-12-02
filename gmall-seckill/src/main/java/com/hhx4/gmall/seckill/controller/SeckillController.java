package com.hhx4.gmall.seckill.controller;

import com.hhx4.common.utils.R;
import com.hhx4.gmall.seckill.service.SeckillService;
import com.hhx4.gmall.seckill.to.SeckillSkuRedisTo;
import com.hhx4.gmall.seckill.vo.SkuInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

}