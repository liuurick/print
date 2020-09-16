package com.wrq.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

public class OrderItem {
    private Integer id;

    private String orderNo;

    private Integer userId;

    private Integer fileId;

    private Integer fileQuantity;

    private BigDecimal currentPrice;

    private Integer sizeInfoType;

    private String userDes;

    private Date createTime;

    private Date updateTime;

    private Integer colorInfoType;

    private Integer pageInfoType;

    public OrderItem(Integer id, String orderNo, Integer userId, Integer fileId, Integer fileQuantity, BigDecimal currentPrice, Integer sizeInfoType, String userDes, Date createTime, Date updateTime, Integer colorInfoType, Integer pageInfoType) {
        this.id = id;
        this.orderNo = orderNo;
        this.userId = userId;
        this.fileId = fileId;
        this.fileQuantity = fileQuantity;
        this.currentPrice = currentPrice;
        this.sizeInfoType = sizeInfoType;
        this.userDes = userDes;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.colorInfoType = colorInfoType;
        this.pageInfoType = pageInfoType;
    }

    public OrderItem() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getFileQuantity() {
        return fileQuantity;
    }

    public void setFileQuantity(Integer fileQuantity) {
        this.fileQuantity = fileQuantity;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Integer getSizeInfoType() {
        return sizeInfoType;
    }

    public void setSizeInfoType(Integer sizeInfoType) {
        this.sizeInfoType = sizeInfoType;
    }

    public String getUserDes() {
        return userDes;
    }

    public void setUserDes(String userDes) {
        this.userDes = userDes;
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

    public Integer getColorInfoType() {
        return colorInfoType;
    }

    public void setColorInfoType(Integer colorInfoType) {
        this.colorInfoType = colorInfoType;
    }

    public Integer getPageInfoType() {
        return pageInfoType;
    }

    public void setPageInfoType(Integer pageInfoType) {
        this.pageInfoType = pageInfoType;
    }
}