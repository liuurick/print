package com.wrq.service;

import com.wrq.commons.ServerResponse;
import com.wrq.form.CreateOrderForm;
import com.wrq.form.GetPriceForm;
import com.wrq.pojo.User;


/**
 * Created by wangqian on 2019/4/4.
 */
public interface IOrderService {

    ServerResponse create(CreateOrderForm form, User user);

    ServerResponse getOrderBeforePay(User user, String orderNo );

    ServerResponse getOrderList(Integer userId,Integer pageNum, Integer pageSize);

    ServerResponse getOrderListForShop(Integer userId, Integer pageNum, Integer pageSize);

    ServerResponse getOrderDetailForBackend( Integer userId, String orderNo );

    ServerResponse checkOrderHasFileForBackend( Integer userId, String fileNewName );

    ServerResponse noticePackingForBackend( User user,  String getKey ,String orderNo );

    ServerResponse refuseOrderForBackend( User user,  String reason , String fileName,String orderNo );

    ServerResponse getOrderFileKey( String orderNo );

    ServerResponse closeOrder( String orderNo );

    ServerResponse overOrder ( String orderNo );
}
