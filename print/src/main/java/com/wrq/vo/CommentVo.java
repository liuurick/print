package com.wrq.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by wangqian on 2019/6/14.
 */
@Data
public class CommentVo {

    /* 评论id */
    @JsonProperty("commentId")
    private Integer id;

    /* 评论用户id */
    private Integer userId;

    /* 评论时间 */
    private Date createTime;

    /* 评论用户IP地址 */
    private String ip;

    /* 评论内容 */
    private String content;

    /* 博客层级 */
    private Integer parentId;

    /* 下级评论 */
    private List<CommentVo> children;

    /* 评论发布状态 */
    private String status;

    /* 评论分享的Id */
    private Integer targetId;

    /* 评论者用户名 */
    private String userName;

    /* 评论者头像 */
    private String headerPic;

}
