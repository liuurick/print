package com.wrq.vo.backend;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wangqian on 2019/4/16.
 */
@Data
public class BackendOrderListVo {

    private String orderNo;

    private String buyerPhone;

    private String buyerEmail;

    private BigDecimal payment;

    private Date createTime;

    private String orderStatus;

    private Integer shopId;

}
