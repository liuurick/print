package com.wrq.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 个人中心 订单列表展示
 * Created by wangqian on 2019/4/11.
 */
@Data
public class OrderListVo {

    private Integer shopId;

    private String shopImg;

    private String orderNo;

    private Date updateTime;

    private BigDecimal payment;

    private String orderStatus;

    private String refuseReason;

    private String getKey;

}
