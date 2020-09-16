package com.wrq.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wangqian on 2019/4/1.
 */
@Data
public class PriceVo {

    private Integer shopId;

    /* 优惠力度阈值 */
    private String bonusThreshold;

    /* 优惠力度 */
    private String bonusValue;

    /* 优惠描述*/
    private String bonusDescription;

    /* A0-A1等尺寸价格 */
    private List otherSizePrice;

    /* 彩色的比例系数 */
    private String colorfulVariable;

    /* 双页的比例系数 */
    private String doubleVariable;

    /* 双页彩色比例系数 */
    private String doubleColorfulVariable;

    /* 单页黑白A4尺寸 */
    private BigDecimal normalSingle;

    /* 单页彩色A4尺寸 */
    private BigDecimal colorfulSingle;

    /* 双页黑白A4尺寸 */
    private BigDecimal normalDouble;

    /* 双页彩色A4尺寸 */
    private BigDecimal colorfulDouble;

}
