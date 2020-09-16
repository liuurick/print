package com.wrq.service;

import com.wrq.commons.ServerResponse;
import com.wrq.pojo.User;

/**
 * Created by wangqian on 2019/3/29.
 */
public interface IUserService {

    ServerResponse getUserById(Integer userId);

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str,String type);

    ServerResponse resetUsername(User user , String username);

    ServerResponse resetEmail(User user , String email);

    ServerResponse resetPhone(User user , String phone);


//    ServerResponse<String> selectQuestion(String username);
//
//    ServerResponse<String> checkAnswer(String username,String question,String answer);
//
//    ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken);
//
    ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user);
//
//    ServerResponse<User> updateInformation(User user);
//
//    ServerResponse<User> getInformation(Integer userId);

    ServerResponse checkAdminRole(User user);

}
