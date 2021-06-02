package com.hhx4.gmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhx4.common.utils.PageUtils;
import com.hhx4.gmall.product.entity.BrandEntity;

import java.util.Map;

/**
 * Ʒ
 *
 * @author hangxing
 * @email hhx4@gmail.com
 * @date 2021-06-02 20:00:12
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

