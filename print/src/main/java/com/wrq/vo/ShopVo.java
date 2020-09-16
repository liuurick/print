package com.wrq.vo;


import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by wangqian on 2019/3/30.
 */
@Data
public class ShopVo {

    /* 店铺ID */
    private Integer shopId;

    private String shopName;

    private String shopDescription;

    /* 店铺评分 */
    private String credit;

    /* 交易人数 */
    private Integer dealNum;

    /* 图片服务器域名 */
    private String imgAddress;

    /* 单页黑白A4尺寸 */
    private BigDecimal normalSingle;

    /* 单页彩色A4尺寸 */
    private BigDecimal colorfulSingle;

    /* 双页黑白A4尺寸 */
    private BigDecimal normalDouble;

    /* 双页彩色A4尺寸 */
    private BigDecimal colorfulDouble;

    /* 0：在营业 1：不营业 */
    private Integer status;
}
