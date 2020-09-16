package com.wrq.enums;

/**
 * Created by wangqian on 2019/3/30.
 */
public enum PageTypeEnum implements CodeEnum {

    SINGLE(0, "单页"),
    DOUBLE(1, "双页"),
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

    PageTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
