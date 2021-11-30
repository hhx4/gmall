package com.hhx4.gmall.seckill.feign;

import com.hhx4.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @created by wt at 2021-11-30 20:09
 **/

@FeignClient("gmall-coupon")
public interface CouponFeignService {

    @GetMapping("coupon/seckillsession/getLatest3DaySession")
    R getLatest3DaySession();
}