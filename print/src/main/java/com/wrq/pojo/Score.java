package com.wrq.pojo;

import java.util.Date;

public class Score {
    private Integer id;

    private Integer userId;

    private Integer ownerId;

    private Integer integral;

    private Integer shareId;

    private String fileNewName;

    private Date createTime;

    private Date updateTime;

    private Integer lookOver;

    public Score(Integer id, Integer userId, Integer ownerId, Integer integral, Integer shareId,String fileNewName, Date createTime, Date updateTime, Integer lookOver) {
        this.id = id;
        this.userId = userId;
        this.shareId = shareId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.lookOver = lookOver;
        this.ownerId = ownerId;
        this.integral = integral;
        this.fileNewName = fileNewName;
    }

    public Score() {
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

    public Integer getShareId() {
        return shareId;
    }

    public void setShareId(Integer shareId) {
        this.shareId = shareId;
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

    public Integer getLookOver() {
        return lookOver;
    }

    public void setLookOver(Integer lookOver) {
        this.lookOver = lookOver;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getFileNewName() {
        return fileNewName;
    }

    public void setFileNewName(String fileNewName) {
        this.fileNewName = fileNewName;
    }
}