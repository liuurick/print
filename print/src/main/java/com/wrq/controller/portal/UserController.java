package com.wrq.controller.portal;

import com.wrq.commons.Const;
import com.wrq.commons.ResponseCode;
import com.wrq.commons.ServerResponse;
import com.wrq.pojo.User;
import com.wrq.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by wangqian on 2019/3/29.
 */
@Controller
@RequestMapping("/user/")
@Slf4j
public class UserController {

    @Autowired
    private IUserService iUserService;


    /**
     * 登陆
     * @param username 用户名
     * @param password 密码
     * @param session session
     * @return 登陆用户信息
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        log.info("请求了  login.do 接口，参数为：username = {}, password = {}",username, password);
        //service -->mybatis ->dao
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return  response;
    }

    /**
     * 获取用户信息
     * @param session 用户session
     * @return 用户信息
     */
    @RequestMapping(value = "get_user_info.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        log.info("请求了 get_user_info.do 接口");
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return  iUserService.getUserById(user.getId());
        }
        return  ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户信息");
    }

    /**
     * 用户登出
     * @param session 用户session
     * @return 结果
     */
    @RequestMapping(value = "logout.do",method = RequestMethod.POST)
    @ResponseBody
    public  ServerResponse<String> logout(HttpSession session){
        log.info("请求了 logout.do 接口");
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /**
     * 跳转到 user 界面
     * @param session
     * @param map
     * @return
     */
    @RequestMapping(value = "info", method = RequestMethod.GET)
    public ModelAndView user( HttpSession session, Map<String, Object> map ){

        log.info("请求了 /user/info 接口");

        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            map.put("msg", ResponseCode.NEED_LOGON_FOR_USER_INFO.getDesc());
            map.put("url", "/user/info" );
            return new ModelAndView("portal/login" , map);
        }else {
            map.put("item", "user");
            return new ModelAndView("portal/user", map);
        }
    }

    /**
     * 修改 username
     * @param username
     * @param session
     * @return
     */
    @RequestMapping(value = "username.do",method = RequestMethod.POST)
    @ResponseBody
    public  ServerResponse username(String username, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("请登陆后进行修改用户名操作！");
        }

       return iUserService.resetUsername(user, username);
    }

    /**
     * 修改邮箱
     * @param email
     * @param session
     * @return
     */
    @RequestMapping(value = "email.do",method = RequestMethod.POST)
    @ResponseBody
    public  ServerResponse email(String email, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("请登陆后进行修改用户名操作！");
        }

        return iUserService.resetEmail(user, email);
    }

    /**
     * 修改手机号
     * @param phone
     * @param session
     * @return
     */
    @RequestMapping(value = "phone.do",method = RequestMethod.POST)
    @ResponseBody
    public  ServerResponse phone(String phone, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("请登陆后进行修改用户名操作！");
        }
        return iUserService.resetPhone(user, phone);
    }

    /**
     * 修改密码
     * @param passwordOld
     * @param passwordNew
     * @param session
     * @return
     */
    @RequestMapping(value = "password.do",method = RequestMethod.POST)
    @ResponseBody
    public  ServerResponse password(String passwordOld, String passwordNew, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("请登陆后进行修改用户名操作！");
        }
        return iUserService.resetPassword(passwordOld, passwordNew, user);
    }

}
