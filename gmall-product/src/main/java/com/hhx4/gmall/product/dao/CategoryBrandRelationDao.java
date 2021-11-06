package com.hhx4.gmall.product.dao;

import com.hhx4.gmall.product.entity.CategoryBrandRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Ʒ
 * 
 * @author hangxing
 * @email hhx4@gmail.com
 * @date 2021-06-02 20:34:21
 */
@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelationEntity> {

    void updateCategory(@Param("catId")Long catId, @Param("name")String name);
}
