package com.hhx4.gmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhx4.common.utils.PageUtils;
import com.hhx4.gmall.product.entity.AttrEntity;
import com.hhx4.gmall.product.vo.AttrGroupRelationVo;
import com.hhx4.gmall.product.vo.AttrRespVo;
import com.hhx4.gmall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author hangxing
 * @email hhx4@gmail.com
 * @date 2021-06-02 20:00:12
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);


    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId);

    List<AttrEntity> getRelationAttr(Long attrgroupId);

    void deleteRelation(AttrGroupRelationVo[] vos);
}

