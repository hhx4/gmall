package com.hhx4.gmall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhx4.common.utils.PageUtils;
import com.hhx4.gmall.coupon.entity.SkuLadderEntity;

import java.util.Map;

/**
 * 
 *
 * @author hangXing
 * @email hhx4@gmail.com
 * @date 2021-06-03 21:47:32
 */
public interface SkuLadderService extends IService<SkuLadderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

