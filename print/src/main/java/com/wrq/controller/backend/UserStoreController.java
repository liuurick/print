package com.wrq.controller.backend;

import com.wrq.commons.Const;
import com.wrq.commons.ServerResponse;
import com.wrq.pojo.User;
import com.wrq.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by wangqian on 2019/4/16.
 */
@Controller
@RequestMapping("/store/user")
public class UserStoreController {

    @Autowired
    private IUserService iUserService;

    /**
     * 后台登陆
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            User user = response.getData();
            if(user.getRole() == Const.Role.ROLE_STORE){
                //说明登陆的是管理员
                session.setAttribute(Const.CURRENT_USER,user);
                return response;
            }else{
                return ServerResponse.createByErrorMessage("不是店主，无法登陆");
            }
        }
        return response;
    }
}