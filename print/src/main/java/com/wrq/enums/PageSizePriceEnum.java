package com.wrq.enums;

import lombok.Getter;

/**
 * Created by wangqian on 2019/4/1.
 */
@Getter
public enum PageSizePriceEnum implements CodeEnum{

    SIZE_A0(0, "A0"),
    SIZE_A1(1, "A1"),
    SIZE_A2(2, "A2"),
    SIZE_A3(3, "A3"),
    SIZE_A4(4, "A4"),
    SIZE_A5(5, "A5"),
    SIZE_A6(6, "A6"),
    SIZE_A7(7, "A7"),
    SIZE_A8(8, "A8"),
    SIZE_A9(9, "A9"),
    SIZE_A10(10, "A10"),
    SIZE_4A0(11, "4A0"),
    SIZE_0A0(12, "0A0"),
    ;

    /* 状态码 */
    private Integer code;

    private String message;

    PageSizePriceEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
