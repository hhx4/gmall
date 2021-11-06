package com.hhx4.gmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhx4.common.utils.PageUtils;
import com.hhx4.gmall.product.entity.ProductAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * spu
 *
 * @author hangxing
 * @email hhx4@gmail.com
 * @date 2021-06-02 20:00:12
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveProductAttr(List<ProductAttrValueEntity> collect);

    List<ProductAttrValueEntity> baseAttrlistforspu(Long spuId);

    void updateSpuAttr(Long spuId, List<ProductAttrValueEntity> entities);
}

