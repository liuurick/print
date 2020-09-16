package com.wrq.dao;

import com.wrq.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    List<OrderItem> selectOrderItemByOrderNo(String orderNo);

    List<OrderItem> getOrderItemByOrderNoUserId( @Param("orderNo") String orderNo, @Param("userId") Integer userId );

    OrderItem selectByFileId(Integer id);
}