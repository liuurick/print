package com.wrq.enums;

/**
 * Created by wangqian on 2019/4/7.
 */
public enum  ShareEnum {

    NOT_SHARE(0, "私有"),
    SHARE(1, "共享"),
    ;

    /* 状态码 */
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private String message;

    ShareEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
