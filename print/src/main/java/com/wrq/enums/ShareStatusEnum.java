package com.wrq.enums;

/**
 * Created by wangqian on 2019/4/22.
 */
public enum ShareStatusEnum {

    NOT_HOT(0, "非热门"),
    HOT(1, "热门"),
    NOT_DELETE(0, "未删除"),
    DELETE(1, "删除"),
    NOT_TOP(0, "非置顶"),
    TOP(1, "置顶");

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    ShareStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
