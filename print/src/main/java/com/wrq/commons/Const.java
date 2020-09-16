package com.wrq.commons;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by wangqina
 */
public class Const {

    public static  final  String CURRENT_USER = "currentUser";
    public static  final  String EMAIL =  "email";
    public static  final  String USERNAME = "username";


    public interface Role{
        int ROLE_CUSTOMER = 0;// 普通用户
        int ROLE_STORE = 1;// 店主
    }

    public  interface  AlipayCallback{
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }

}
