package com.hhx4.gmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhx4.common.utils.PageUtils;
import com.hhx4.gmall.ware.entity.WareSkuEntity;
import com.hhx4.gmall.ware.vo.SkuHasStockVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author hangXing
 * @email hhx4@gmail.com
 * @date 2021-06-03 22:35:48
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("skuNum") Integer skuNum);

    List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);
}

