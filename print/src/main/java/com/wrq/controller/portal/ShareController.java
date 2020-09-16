package com.wrq.controller.portal;

import com.google.common.collect.Maps;
import com.wrq.commons.Const;
import com.wrq.commons.ResponseCode;
import com.wrq.commons.ServerResponse;
import com.wrq.config.ParameterConfig;
import com.wrq.exception.PrintException;
import com.wrq.form.ShareCreateForm;
import com.wrq.pojo.User;
import com.wrq.service.IFileService;
import com.wrq.service.IShareService;
import com.wrq.vo.DetailVo;
import com.wrq.vo.ShopVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by wangqian on 2019/4/21.
 */
@Controller
@RequestMapping("/share/")
@Slf4j
public class ShareController {

    @Autowired
    private IShareService iShareService;




    /**
     * 跳转分享文件页面
     * @param session
     * @param map
     * @return
     */
    @RequestMapping("create")
    public ModelAndView share(HttpSession session, Map<String, Object> map){

        User user = (User)session.getAttribute(Const.CURRENT_USER);

        if(user == null){
            map.put("msg", ResponseCode.NEED_LOGON_FOR_SHARE.getDesc());
            map.put("url", "/share/create");
            return new ModelAndView("portal/login" , map);
        }else {
            return new ModelAndView("portal/share" , map);
        }
    }

    /**
     * 创建分享 提交参数
     * @param shareCreateForm
     * @param bindingResult
     * @param session
     * @return
     */
    @PostMapping("create")
    @ResponseBody
    public ServerResponse create(@Valid ShareCreateForm shareCreateForm, BindingResult bindingResult, HttpSession session){

        if ( bindingResult.hasErrors() ) {
            return ServerResponse.createByErrorMessage(bindingResult.getFieldError().getDefaultMessage());
        }
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getDesc());
        }
        return  iShareService.insertShareRecode(shareCreateForm, user);
    }

    /**
     * 获取 分享列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("list.do")
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        return  iShareService.getShareList(pageNum, pageSize);
    }


    /**
     * 根据 tag 进行获取列表，考研、考公、四六级
     * @param pageNum
     * @param pageSize
     * @param type
     * @return
     */
    @GetMapping("sort")
    @ResponseBody
    public ServerResponse listSort(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize, int type){
        return  iShareService.getShareListTypeSort(pageNum, pageSize, type);
    }


    /**
     * 当前分享 详情
     * @param id 分享ID
     * @return 分享详情
     */
    @GetMapping("detail/{id}")
    public ModelAndView detail(@PathVariable("id") Integer id, HttpSession session, Map<String, Object> map) {

        User user = (User)session.getAttribute(Const.CURRENT_USER);

        if(user == null){
            map.put("msg", ResponseCode.NEED_LOGON_FOR_SHARE_DETAIL.getDesc());
            map.put("url", "/share/detail/" + id);
            return new ModelAndView("portal/login" , map);
        }

        ServerResponse result = iShareService.getShareDetailById(id, user);

        if ( result.isSuccess() ){
            map.put("shareInfo", result.getData());
            return new ModelAndView("portal/share-detail", map);
        }else {
            map.put("msg", result.getMsg());
            map.put("url", "/share/list");
            return new ModelAndView("portal/common/page/error", map);
        }
    }


    /**
     * 下载之前进行判断
     * @param id
     * @param session
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "before", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse before (Integer id, HttpSession session, HttpServletRequest request, HttpServletResponse response ){

        User user = (User)session.getAttribute(Const.CURRENT_USER);

        if(user == null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getDesc());
        }

        return iShareService.prepareForDownload( id, user );
    }


    /**
     * 下载分享
     * @param id
     * @param session
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "download/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse download (@PathVariable("id") Integer id, HttpSession session, HttpServletRequest request, HttpServletResponse response ){

        User user = (User)session.getAttribute(Const.CURRENT_USER);

        if(user == null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGIN.getDesc());
        }

        String path = request.getSession().getServletContext().getRealPath("upload");

        ServerResponse result ;

        try {
            result = iShareService.downloadShareByShopId(path, id, user, response);
        }catch (PrintException e){
            log.error("[分享下载] 发生异常 = {}", e);
            return ServerResponse.createByErrorMessage(e.getMessage());
        }

        if ( !result.isSuccess() ){
            return ServerResponse.createByErrorMessage("下载失败");
        }

        return ServerResponse.createBySuccess("下载成功");
    }

    /**
     * 个人中心 交易记录下载
     * @param id
     * @param session
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "download", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse downloadForScoreHistory(Integer id, HttpSession session,  HttpServletRequest request, HttpServletResponse response){

        User user = (User)session.getAttribute(Const.CURRENT_USER);

        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请先登陆");
        }

        String path = request.getSession().getServletContext().getRealPath("upload");

        ServerResponse result = null;

        result = iShareService.downloadForUserCenter(path, id, user.getId(), response);

        if ( !result.isSuccess() ){
            return result;
        }

        return ServerResponse.createBySuccess("下载成功");
    }




}
