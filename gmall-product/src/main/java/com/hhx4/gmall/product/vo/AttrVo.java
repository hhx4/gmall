package com.hhx4.gmall.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @created by wt at 2021-11-05 16:24
 **/
@Data
public class AttrVo {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId
    private Long attrId;
    /**
     *
     */
    private String attrName;
    /**
     *
     */
    private Integer searchType;
    /**
     *
     */
    private String icon;
    /**
     *
     */
    private String valueSelect;
    /**
     *
     */
    private Integer attrType;
    /**
     *
     */
    private Long enable;
    /**
     *
     */
    private Long catelogId;
    /**
     *
     */
    private Integer showDesc;
    private Long attrGroupId;
}