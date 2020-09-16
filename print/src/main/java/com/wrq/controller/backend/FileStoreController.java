package com.wrq.controller.backend;

import com.wrq.commons.Const;
import com.wrq.commons.ResponseCode;
import com.wrq.commons.ServerResponse;
import com.wrq.pojo.User;
import com.wrq.service.IFileService;
import com.wrq.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

/**
 * Created by wangqian on 2019/4/18.
 */
@Controller
@RequestMapping("/store/file")
@Slf4j
public class FileStoreController {

    @Autowired
    private IFileService iFileService;

    @Autowired
    protected IOrderService iOrderService;

    /***
     * @param file file 新名字
     * @param session
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "download", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse download(String file, String orderNo, HttpSession session, HttpServletRequest request, HttpServletResponse response){

        User user = (User)session.getAttribute(Const.CURRENT_USER);

        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请先登陆");
        }

        if( user.getRole() != Const.Role.ROLE_STORE ){
            return ServerResponse.createByErrorMessage("不是店主，无法访问此文件");
        }

        ServerResponse serverResponse = iOrderService.checkOrderHasFileForBackend(user.getId(), file);

        if ( !serverResponse.isSuccess() ){
            return serverResponse;
        }

        String path = request.getSession().getServletContext().getRealPath("upload");

        ServerResponse result = null;

        try {
            result = iFileService.backendDownload(path, file, orderNo,  response);
        } catch (UnsupportedEncodingException e) {
            log.error(" 文件名编码失败 ");
        }

        if ( !result.isSuccess() ){
            return result;
        }

        return ServerResponse.createBySuccess("下载成功");
    }

}
