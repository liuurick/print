package com.wrq.dao;

import com.wrq.pojo.Score;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Score record);

    int insertSelective(Score record);

    Score selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Score record);

    int updateByPrimaryKey(Score record);

    Score selectByUserIdAndShareId(@Param("userId") Integer userId,@Param("shareId") Integer shareId );

    List<Score> selectScoreListByUserId(Integer userId );
}