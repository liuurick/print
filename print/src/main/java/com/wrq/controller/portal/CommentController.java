package com.wrq.controller.portal;

import com.github.pagehelper.PageInfo;
import com.wrq.commons.Const;
import com.wrq.commons.ResponseCode;
import com.wrq.commons.ServerResponse;
import com.wrq.form.CommentForm;
import com.wrq.pojo.User;
import com.wrq.service.ICommentService;
import com.wrq.vo.CommentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by wangqian on 2019/6/14.
 */
@Controller
@RequestMapping("/comment/")
@Slf4j
public class CommentController {

    @Autowired
    private ICommentService iCommentService;


    /**
     * 创建评论： parentId刚开始为 0
     * @param commentForm
     * @param bindingResult
     * @param session
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse form(@Valid CommentForm commentForm, BindingResult bindingResult, HttpSession session) {


        if ( bindingResult.hasErrors() ) {
            return ServerResponse.createByErrorMessage(bindingResult.getFieldError().getDefaultMessage());
        }

        User user = (User)session.getAttribute(Const.CURRENT_USER);

        if(user == null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGON_FOR_CREATE.getDesc());
        }else {
            return iCommentService.createComment(commentForm, user.getId());
        }
    }

    /**
     * 查看评论
     * @param pageNum
     * @param pageSize
     * @param targetId
     * @param session
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "5") int pageSize,Integer targetId, HttpSession session ) {

        User user = (User)session.getAttribute(Const.CURRENT_USER);

        if(user == null){
            return ServerResponse.createByErrorMessage(ResponseCode.NEED_LOGON_FOR_CREATE.getDesc());
        }else {
            PageInfo<CommentVo> commentVoPageInfo = iCommentService.queryCommentFirstLevel(targetId, pageNum, pageSize);
            return ServerResponse.createBySuccess(commentVoPageInfo);
        }
    }



}
