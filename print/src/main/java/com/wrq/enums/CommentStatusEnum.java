package com.wrq.enums;

import lombok.Getter;

/**
 * Created by wangqian on 2019/6/14.
 */
@Getter
public enum CommentStatusEnum {

    NOT_DELETE(0, "没有删除"),
    DELETE(1, "已经删除"),
    ;

    /* 状态码 */
    private Integer code;

    private String message;

    CommentStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
