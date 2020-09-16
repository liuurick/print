package com.wrq.service.impl;

import com.wrq.commons.Const;
import com.wrq.commons.ServerResponse;
import com.wrq.config.ParameterConfig;
import com.wrq.dao.UserMapper;
import com.wrq.pojo.User;
import com.wrq.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by wangqian on 2019/3/29.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ParameterConfig parameterConfig;

    @Override
    public ServerResponse getUserById(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);

        if ( user == null ){
            return ServerResponse.createByErrorMessage("无此用户信息，请稍后再试");
        }

        String headerPic = user.getHeaderPic();

        user.setHeaderPic( parameterConfig.getImageHost() + headerPic );

        return ServerResponse.createBySuccess(user);
    }

    /**
     * 登陆
     * @param username 用户名
     * @param password 密码
     * @return 登陆用户的信息
     */
    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0){
            return  ServerResponse.createByErrorMessage("用户名不存在");
        }
//        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username,password);
        if(user == null){
            return  ServerResponse.createByErrorMessage("密码错误");
        }
        user.setHeaderPic( parameterConfig.getImageHost() + user.getHeaderPic());
        user.setPassword(StringUtils.EMPTY);
        return  ServerResponse.createBySuccess("登陆成功",user);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    public ServerResponse<String> register(User user){

//        int resultCount = userMapper.checkUsername(user.getUsername());
//        if(resultCount > 0){
//            return  ServerResponse.createByErrorMessage("用户名已存在");
//        }
//        resultCount = userMapper.checkEmail(user.getEmail());
//        if(resultCount > 0){
//            return  ServerResponse.createByErrorMessage("email已存在");
//        }
//   上面注释掉的用下面的复用替代了

        ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if(!validResponse.isSuccess()){
            return  validResponse;
        }
        validResponse = this.checkValid(user.getEmail(),Const.EMAIL);
        if(!validResponse.isSuccess()){
            return  validResponse;
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
//        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        user.setPassword(user.getPassword());

        int  resultCount = userMapper.insert(user);
        if(resultCount == 0){
            return  ServerResponse.createByErrorMessage("注册失败");
        }
        return  ServerResponse.createBySuccessMessage("注册成功");

    }

    /**
     * 校验复用，检查邮箱用户名是不是有效
     * @param str
     * @param type
     * @return
     */
    public ServerResponse<String> checkValid(String str,String type){
        if(StringUtils.isNotBlank(type)){
            //开始校验
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if(resultCount > 0){
                    return  ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if(Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(str);
                if(resultCount > 0){
                    return  ServerResponse.createByErrorMessage("email已存在");
                }
            }
        }else{
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return  ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServerResponse resetUsername(User user, String username) {

        if ( StringUtils.isEmpty(username) ){
            return ServerResponse.createByErrorMessage("请输入新用户名后再进行保存！");
        }

        int result = userMapper.updateUsernameByUserId(user.getId(), username);

        if ( result <= 0 ){
            return ServerResponse.createByErrorMessage("更新用户名时出现错误，请稍后再试！");
        }

        return ServerResponse.createBySuccess("修改成功");
    }

    @Override
    public ServerResponse resetEmail(User user, String email) {

        ServerResponse validResponse = this.checkValid(user.getUsername(), Const.EMAIL);
        if( !validResponse.isSuccess() ){
            return  validResponse;
        }

        int result = userMapper.updateEmailByUserId(user.getId(), email);

        if ( result <= 0 ){
            return ServerResponse.createByErrorMessage("更新时出现错误");
        }
        return ServerResponse.createBySuccess("更新成功");
    }

    @Override
    public ServerResponse resetPhone(User user, String phone) {

        int result = userMapper.updatePhoneByUserId(user.getId(), phone);

        if ( result <= 0 ){
            return ServerResponse.createByErrorMessage("更新时出现错误");
        }

        return ServerResponse.createBySuccess("更新成功");
    }

//    /**
//     * 忘记密码
//     * @param username
//     * @return
//     */
//    public ServerResponse<String> selectQuestion(String username){
//        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
//        if(validResponse.isSuccess()){
//            //用户不存在
//            return  ServerResponse.createByErrorMessage("用户不存在");
//        }
//        String question = userMapper.selectQuestionByUsername(username);
//        if(StringUtils.isNotBlank(question)){
//            return ServerResponse.createBySuccess(question);
//        }
//        return ServerResponse.createByErrorMessage("找回密码的问题为空");
//    }
//
//    /**
//     * 提交问题答案
//     * @param username
//     * @param question
//     * @param answer
//     * @return
//     */
//    public ServerResponse<String> checkAnswer(String username,String question,String answer){
//        int resultCount = userMapper.checkAnswer(username,question,answer);
//        if(resultCount > 0){
//            //说明问题和问题答案是这个用户的，并且是正确的
//            String forgetToken = UUID.randomUUID().toString();
//            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
//            return  ServerResponse.createBySuccess(forgetToken);
//        }
//        return ServerResponse.createByErrorMessage("问题的答案错误");
//    }
//
//
//    /**
//     * 忘记密码的修改密码，需要对比token
//     * @param username
//     * @param passwordNew
//     * @param forgetToken
//     * @return
//     */
//    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
//        if(StringUtils.isBlank(forgetToken)){
//            return ServerResponse.createByErrorMessage("参数错误，token需要传递");
//        }
//        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
//        if(validResponse.isSuccess()){
//            //用户不存在
//            return  ServerResponse.createByErrorMessage("用户不存在");
//        }
//        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);
//        if(StringUtils.isBlank(token)){
//            return ServerResponse.createByErrorMessage("token无效或者过期");
//        }
//        if(StringUtils.equals(forgetToken,token)){
//            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
//            int rowCount = userMapper.updatePasswordByUsername(username,md5Password);
//            if(rowCount > 0 ){
//                return ServerResponse.createBySuccessMessage("修改密码成功");
//            }
//        }else{
//            return  ServerResponse.createByErrorMessage("token错误，请重新获取重置密码的token");
//        }
//        return ServerResponse.createByErrorMessage("修改密码失败");
//    }
//
//
    /**
     * 登陆状态重设密码
     * @param passwordOld
     * @param passwordNew
     * @param user
     * @return
     */
    public ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user){
        //防止横向越权，要校验这个用户的旧密码，一定要指定是这个用户.因为我们会查询一个count(1),如果不指定id，那么结果就是true啦，count>0;
//        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        int resultCount = userMapper.checkPassword(passwordOld, user.getId());
        if(resultCount == 0){
            return  ServerResponse.createByErrorMessage("旧密码错误");
        }
//        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));

        int updateCount = userMapper.updatePasswordByPrimaryKey(user.getId(), passwordNew);
        if(updateCount > 0){
            return ServerResponse.createBySuccessMessage("密码更新成功");
        }
        return ServerResponse.createByErrorMessage("密码更新失败");

    }
//
//    /**
//     * 更新个人信息
//     * @param user
//     * @return
//     */
//    public ServerResponse<User> updateInformation(User user){
//        //username 是不能被更新的
//        //email也要进行一个校验，校验心得email是不是已经存在，并且存在的email如果相同的话，不能使我们当前这个用户的
//        int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());
//        if(resultCount > 0){
//            return  ServerResponse.createByErrorMessage("email已经存在,请更换email再尝试更新");
//        }
//        User updateUser = new User();
//        updateUser.setId(user.getId());
//        updateUser.setEmail(user.getEmail());
//        updateUser.setPhone(user.getPhone());
//        updateUser.setQuestion(user.getQuestion());
//        updateUser.setAnswer(user.getAnswer());
//        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
//        if(updateCount > 0){
//            return  ServerResponse.createBySuccess("更新个人信息成功",updateUser);
//        }
//        return  ServerResponse.createByErrorMessage("更新个人信息失败");
//    }
//
//
//    /**
//     * 查询用户详细信息
//     * @param userId
//     * @return
//     */
//    public ServerResponse<User> getInformation(Integer userId){
//        User user = userMapper.selectByPrimaryKey(userId);
//        return  ServerResponse.createByErrorMessage("找不到当前用户");
//        if(user == null){
//        }
//        user.setPassword(StringUtils.EMPTY);
//        return  ServerResponse.createBySuccess(user);
//    }



    //backend

    /**
     * 校验是否是店主
     * @param user
     * @return
     */
    public ServerResponse checkAdminRole(User user){
        if(user != null && user.getRole().intValue() == Const.Role.ROLE_STORE){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

}
