package com.wrq.dao;

import com.wrq.pojo.Share;

import java.util.List;

public interface ShareMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Share record);

    int insertSelective(Share record);

    Share selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Share record);

    int updateByPrimaryKey(Share record);

    List<Share> selectShareList();

    Share selectByPrimaryKeyAndNotDelete(Integer id);

    int addViewNumByShareId(Integer id);

    List<Share> selectShareListTypeSort(Integer type);

}