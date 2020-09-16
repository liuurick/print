package com.wrq.dao;

import com.wrq.pojo.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);

    /* 根据信用积分查询店铺 */
    List<Shop> selectShopListByTypeSort(@Param(value = "type") String type);

    /* 查询所有店铺 */
    List<Shop> selectAllShopByCredit();

    /* 查询除了此ID外的其他店铺 */
    List<Shop> selectOtherShopSortByCredit(Integer shopId);

    Shop selectShopByUserId(Integer userId);

    int changeStoreStatus(@Param("id") Integer id, @Param("status")Integer status);

    int addDealNumByPrimaryKey (@Param("id") Integer id);

    int updateCreditByPrimaryKey(@Param("id") Integer id, @Param("credit")String credit);
}