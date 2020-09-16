package com.wrq.service;

import com.wrq.commons.ServerResponse;

import java.util.Map;

/**
 * Created by wangqian on 2019/4/9.
 */
public interface IPayService {

    ServerResponse pay(String orderNo, Integer userId, String path);

    ServerResponse aliCallback( Map<String,String> params );

    ServerResponse queryOrderPayStatus(Integer userId,String orderNo );

    ServerResponse trade_refund(String orderNo, Integer userId);

}
