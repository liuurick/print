package com.wrq.enums;

import lombok.Getter;

/**
 * Created by wangqian on 2019/4/19.
 */
@Getter
public enum MessageTypeEnum implements CodeEnum{


    SEND(0, "通知用户"),
    REFUSE(1, "拒绝用户"),
    ;

    /* 状态码 */
    private Integer code;

    private String message;

    MessageTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}