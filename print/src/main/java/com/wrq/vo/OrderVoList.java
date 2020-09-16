package com.wrq.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by wangqian on 2019/4/9.
 */
@Data
public class OrderVoList {

    private List<OrderVo> orderVoList;

    private BigDecimal totalPrice;

    private String orderNo;

    private String phone;

    private String orderStatus;

}
