package com.hhx4.gmall.product.dao;

import com.hhx4.gmall.product.entity.SpuInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * spu
 * 
 * @author hangxing
 * @email hhx4@gmail.com
 * @date 2021-06-02 20:34:21
 */
@Mapper
public interface SpuInfoDao extends BaseMapper<SpuInfoEntity> {

    void updateSpuStatus(@Param("spuId") Long spuId, @Param("code") int code);
}
