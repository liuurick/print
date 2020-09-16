package com.wrq.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 前去支付，订单页面展示
 * Created by wangqian on 2019/4/8.
 */
@Data
public class OrderVo {

    private String fileUniqueKey;

    private String fileTypeImg;

    private String fileName;

    private String singleOrDouble;

    private String colorOrBlack;

    private String pageSize;

    private Integer fileQuantity;

    private BigDecimal orderPrice;

    private String userDesc;

    private String fileNewName; /* 用来下载 */

}
