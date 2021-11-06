package com.hhx4.gmall.product.vo;

import lombok.Data;

/**
 * @created by wt at 2021-11-05 16:49
 **/
@Data
public class AttrRespVo extends AttrVo{
    private String catelogName;
    private String groupName;
    private Long[] catelogPath;
}