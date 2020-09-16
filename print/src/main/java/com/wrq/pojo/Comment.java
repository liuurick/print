package com.wrq.pojo;

import java.util.Date;

public class Comment {
    private Integer id;

    private Integer userId;

    private Integer targetId;

    private String ip;

    private String content;

    private Integer parentId;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer commentType;

    public Comment(Integer id, Integer userId, Integer targetId, String ip, String content, Integer parentId, Integer status, Date createTime, Date updateTime, Integer commentType) {
        this.id = id;
        this.userId = userId;
        this.targetId = targetId;
        this.ip = ip;
        this.content = content;
        this.parentId = parentId;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.commentType = commentType;
    }

    public Comment() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCommentType() {
        return commentType;
    }

    public void setCommentType(Integer commentType) {
        this.commentType = commentType;
    }
}