package com.hhx4.gmall.seckill.feign;

import com.hhx4.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品远程调用服务
 **/
@FeignClient("gmall-product")
public interface ProductFeignService {

    @RequestMapping("/product/skuInfo/info/{skuId}")
    R getSkuinfo(@PathVariable("skuId") Long skuId);
}