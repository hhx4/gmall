package com.hhx4.gmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhx4.common.utils.PageUtils;
import com.hhx4.gmall.product.entity.SpuInfoDescEntity;

import java.util.Map;

/**
 * spu
 *
 * @author hangxing
 * @email hhx4@gmail.com
 * @date 2021-06-02 20:00:12
 */
public interface SpuInfoDescService extends IService<SpuInfoDescEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

