package com.wrq.dao;

import com.wrq.pojo.OrderMaster;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMasterMapper {
    int deleteByPrimaryKey(String orderNo);

    int insert(OrderMaster record);

    int insertSelective(OrderMaster record);

    OrderMaster selectByPrimaryKey(String orderNo);

    int updateByPrimaryKeySelective(OrderMaster record);

    int updateByPrimaryKey(OrderMaster record);

    OrderMaster selectByUserIdAndOrderNo(@Param("userId") Integer userId, @Param("orderNo") String orderNo);

    List<OrderMaster> selectOrderListByUserId(Integer userId);

    List<OrderMaster> selectOrderListByShopId(Integer shopId);

    OrderMaster checkByOrderNoAndShopId(@Param("shopId") Integer shopId, @Param("orderNo") String orderNo);

    int updateOrderStatusAndGetKeyByPrimaryKey(@Param("getKey") String getKey ,@Param("orderStatus") Integer order_status, @Param("orderNo") String orderNo);

    int updateOrderStatusAndReasonByPrimaryKey(@Param("reason") String reason ,@Param("orderStatus") Integer order_status, @Param("orderNo") String orderNo);

    int updateOrderStatusByOrderNo( @Param("orderStatus") Integer order_status, @Param("orderNo") String orderNo );
}