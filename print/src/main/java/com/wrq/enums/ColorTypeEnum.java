package com.wrq.enums;

import lombok.Getter;

/**
 * Created by wangqian on 2019/3/30.
 */
@Getter
public enum ColorTypeEnum implements CodeEnum{

    BLACK(0, "黑白"),
    COLORFUL(1, "彩色"),
    ;

    /* 状态码 */
    private Integer code;

    private String message;

    ColorTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
