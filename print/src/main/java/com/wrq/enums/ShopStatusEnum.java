package com.wrq.enums;

/**
 * Created by wangqian on 2019/3/31.
 */
public enum ShopStatusEnum implements CodeEnum {

    OPEN(0, "在营业"),
    CLOSE(1, "未营业"),
    ;

    /* 状态码 */
    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    ShopStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
