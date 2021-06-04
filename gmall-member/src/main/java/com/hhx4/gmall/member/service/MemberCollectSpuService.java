package com.hhx4.gmall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hhx4.common.utils.PageUtils;
import com.hhx4.gmall.member.entity.MemberCollectSpuEntity;

import java.util.Map;

/**
 * 
 *
 * @author hangXing
 * @email hhx4@gmail.com
 * @date 2021-06-03 21:56:43
 */
public interface MemberCollectSpuService extends IService<MemberCollectSpuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

