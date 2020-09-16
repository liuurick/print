package com.wrq.dao;

import com.wrq.pojo.Color;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;


public interface ColorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Color record);

    int insertSelective(Color record);

    Color selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Color record);

    int updateByPrimaryKey(Color record);

    /* 根据店铺ID查询黑白或则彩色 */
    Color selectBlackOrColorByShopId(@Param("shopId") Integer shopId, @Param("colorType") Integer colorType);

    int updatePriceByPrimaryKey(@Param("shopId") Integer shopId, @Param("price") BigDecimal price, @Param("colorType") String colorType);

}