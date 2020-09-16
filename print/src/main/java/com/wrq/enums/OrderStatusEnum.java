package com.wrq.enums;

import lombok.Getter;

/**
 * Created by wangqian on 2019/4/8.
 */
@Getter
public enum OrderStatusEnum implements CodeEnum{

    /* 40 50 60 70 都是支付完毕后的状态 */
    CANCELED(0,"已取消"), /* 未支付，用户点击取消订单 */
    NO_PAY(10,"未支付"),
    PAID(20,"已付款"),
    PROCESSING_ORDER(40,"待取货"),
    ORDER_REFUSED(50,"店主拒绝"),
    ORDER_SUCCESS(60,"订单完结"),
    ORDER_CLOSE(70 , "订单关闭");

    /* 状态码 */
    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
