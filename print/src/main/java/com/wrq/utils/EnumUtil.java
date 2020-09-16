package com.wrq.utils;

import com.wrq.enums.CodeEnum;

/**
 * Created by wangqian on 2019/3/4.
 */
public class EnumUtil {

    public static <T extends CodeEnum>T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()){
            if (code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
