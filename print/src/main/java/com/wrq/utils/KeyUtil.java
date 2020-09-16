package com.wrq.utils;

import java.util.Random;

/**
 *  Created by wangqian on 2019/4/8.
 */
public class KeyUtil {

    /**
     * 生成唯一字符串
     * 格式：时间 + 6位随机数
     * @return
     */
    public static synchronized String genUniqueKey(){
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }
}
