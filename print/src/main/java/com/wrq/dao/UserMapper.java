package com.wrq.dao;

import com.wrq.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username);

    int checkEmail(String email);
    //Param注解多个参数使用
    User selectLogin(@Param("username") String username, @Param("password")String password);

//    String selectQuestionByUsername(String username);
//
//    int checkAnswer(@Param("username") String username,@Param("question") String question,@Param("answer") String answer);
//
//    int updatePasswordByUsername(@Param("username")String username,@Param("passwordNew")String passwordNew);
//
    int checkPassword(@Param("password")String password,@Param("userId")Integer userId);
//
//    int checkEmailByUserId(@Param("email")String email,@Param("userId")Integer userId);

    int updateByPrimaryKeyAndIntegral(@Param("id") Integer id, @Param("integral")String integral);

    int updateUsernameByUserId(@Param("id") Integer id, @Param("username")String username);

    int updateEmailByUserId(@Param("id") Integer id, @Param("email")String email);

    int updatePhoneByUserId(@Param("id") Integer id, @Param("phone")String phone);

    int updatePasswordByPrimaryKey(@Param("id") Integer id, @Param("password")String password);

}