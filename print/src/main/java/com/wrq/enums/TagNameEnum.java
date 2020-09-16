package com.wrq.enums;

import lombok.Getter;

/**
 * Created by wangqian on 2019/4/19.
 */
@Getter
public enum TagNameEnum implements CodeEnum{


    TAG_OTHERS(0,"其它"), /* 未支付，用户点击取消订单 */
    TAG_POSTGRADUATE(1,"考研"),
    TAG_EXAMINATION(2,"考试"),
    TAG_PAPER(3,"论文"),
    TAG_CIVIL_SERVANT(4,"考公"),
    TAG_CET(5,"四六级");

    /* 状态码 */
    private Integer code;

    private String message;

    TagNameEnum (Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
