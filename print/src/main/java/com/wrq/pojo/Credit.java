package com.wrq.pojo;

import java.util.Date;

public class Credit {
    private Integer id;

    private Integer userId;

    private Integer shopId;

    private String score;

    private Date createTime;

    private Date updateTime;

    public Credit(Integer id, Integer userId, Integer shopId, String score, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.shopId = shopId;
        this.score = score;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Credit() {
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

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
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
}