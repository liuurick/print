package com.wrq.vo.backend;

import com.wrq.vo.OrderVo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by wangqian on 2019/4/17.
 */
@Data
public class BackendOrderVo {

    private String orderNo;

    private BigDecimal payment;

    private Date createTime;

    private Date paymentTime;

    private String orderStatus;

    private String refuseReason;

    private String getKey;

    private String username;

    private String userPhone;

    private String userEmail;

    private Integer shopId;

    private List<OrderVo> orderVoList;

}
