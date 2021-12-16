package com.hhx4.gmall.order.vo;

import com.hhx4.gmall.order.entity.OrderEntity;
import lombok.Data;

@Data
public class SubmitOrderResponseVo {

    private OrderEntity order;
    private Integer code;//0成功   错误状态码
}
