package com.wrq.enums;

/**
 * Created by wangqian on 2019/4/1.
 */
public enum ServiceNotExistEnum {

    SERVICE_NOT_EXIST(-1, "无服务"),
    PAGE_SIZE_SERVICE_NOT_EXIST(-2, "不提供该尺寸打印服务"),
    COLORFUL_SERVICE_NOT_EXIST(-3, "不提供彩印服务"),
    DOUBLE_PAGE_SERVICE_NOT_EXIST(-4, "不提供双页打印服务"),
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

    ServiceNotExistEnum(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

}
