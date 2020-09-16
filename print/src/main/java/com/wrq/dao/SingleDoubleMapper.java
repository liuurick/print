package com.wrq.dao;

import com.wrq.pojo.SingleDouble;
import org.apache.ibatis.annotations.Param;

public interface SingleDoubleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SingleDouble record);

    int insertSelective(SingleDouble record);

    SingleDouble selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SingleDouble record);

    int updatePrice(@Param("shopId") Integer shopId, @Param("pageType") String pageType,  @Param("price") String price);

    int updateByPrimaryKey(SingleDouble record);

    SingleDouble selectSingleOrDoubleByShopId(@Param("shopId") Integer shopId, @Param("pageType") Integer pageType);
}