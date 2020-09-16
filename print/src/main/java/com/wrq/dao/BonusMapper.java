package com.wrq.dao;

import com.wrq.pojo.Bonus;
import org.apache.ibatis.annotations.Param;

public interface BonusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bonus record);

    int insertSelective(Bonus record);

    Bonus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bonus record);

    int updateByPrimaryKey(Bonus record);

    int updateBonus(@Param("shopId") Integer shopId,@Param("threshold") String  threshold,@Param("bonus") String bonus );

    Bonus selectBonusByShopId(Integer id);
}