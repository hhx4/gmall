package com.hhx4.gmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhx4.common.utils.PageUtils;
import com.hhx4.gmall.ware.entity.PurchaseEntity;
import com.hhx4.gmall.ware.vo.MergeVo;
import com.hhx4.gmall.ware.vo.PurchaseDoneVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author hangXing
 * @email hhx4@gmail.com
 * @date 2021-06-03 22:35:48
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceivePurchase(Map<String, Object> params);

    @Transactional
    void mergePurchase(MergeVo mergeVo);

    void received(List<Long> ids);

    @Transactional
    void done(PurchaseDoneVo doneVo);

}

