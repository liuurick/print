package com.wrq.enums;

import lombok.Getter;

/**
 * Created by wangqian on 2019/2/2.
 */
@Getter
public enum ResultEnum {

    SHARE_NOT_EXIST(1, "待下载的分享已被删除"),
    NOT_RIGHT_OPERATING(2, "不可以下载自己的文件"),
    FILE_NOT_EXIST(3, "待下载的文件找不到"),
    FILE_NOT_SHARE(4, "文件的主人取消此文件的分享啦"),
    INTEGRAL_NOT_ENOUGH(5, "当前用户的积分余额不足"),
    UPRATE_FILE_OWNER_INTEGRAL_ERROR(6, "更新文件主积分时出现错误"),
    UPRATE_CURRENT_USER_INTEGRAL_ERROR(7, "更新积分操作时出现错误"),
    UPRATE_DOWNLOAD_NUMBER_ERROR(8, "更新下载数时出现错误"),
    ADD_SCORE_RECORD_ERROR(9, "添加积分记录时出现错误"),
    FILE_DOWNLOAD_ERROR(10, "下载文件时出现错误"),
    FILE_DOWNLOAD_ALREADY(10, "兑换过此文件，请到「个人中心」下载"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
