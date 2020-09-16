package com.wrq.service;

import com.github.pagehelper.PageInfo;
import com.wrq.commons.ServerResponse;
import com.wrq.form.CommentForm;
import com.wrq.vo.CommentVo;

/**
 * Created by wangqian on 2019/6/14.
 */
public interface ICommentService {

    PageInfo<CommentVo> queryCommentFirstLevel(Integer targetId, Integer pageNum, Integer pageSize);

    ServerResponse createComment(CommentForm commentForm, Integer userId);

}
