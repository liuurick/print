package com.wrq.dao;

import com.wrq.pojo.File;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(File record);

    int insertSelective(File record);

    File selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(File record);

    int updateByPrimaryKey(File record);

    List<File> selectFileByUserId(Integer userId);

    File selectFileByUserIdFileNewName(@Param(value = "userId") Integer userId, @Param(value = "fileName") String fileName);

    File selectFileByFileNewName(String fileName);

    List<File> selectNotShareFileByUserId(Integer userId);

    int updateFileScoreAndStatus(@Param(value = "fileId") Integer fileId, @Param(value = "score") Integer score);
}