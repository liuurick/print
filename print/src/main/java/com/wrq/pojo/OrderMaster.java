package com.wrq.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

public class OrderMaster {
    private String orderNo;

    private Integer shopId;

    private Integer buyerId;

    private String buyerName;

    private String buyerPhone;

    private String buyerEmail;

    private BigDecimal payment;

    private Integer paymentType;

    private Integer orderStatus;

    private Date paymentTime;

    private Date createTime;

    private Date updateTime;

    private String refuseReason;

    private String getKey;

    public OrderMaster(String orderNo, Integer shopId, Integer buyerId, String buyerName, String buyerPhone, String buyerEmail, BigDecimal payment, Integer paymentType, Integer orderStatus, Date paymentTime, Date createTime, Date updateTime, String refuseReason, String getKey) {
        this.orderNo = orderNo;
        this.shopId = shopId;
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.buyerPhone = buyerPhone;
        this.buyerEmail = buyerEmail;
        this.payment = payment;
        this.paymentType = paymentType;
        this.orderStatus = orderStatus;
        this.paymentTime = paymentTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.refuseReason = refuseReason;
        this.getKey = getKey;
    }

    public OrderMaster() {
        super();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
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

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public String getGetKey() {
        return getKey;
    }

    public void setGetKey(String getKey) {
        this.getKey = getKey;
    }

    @Override
    public String toString() {
        return "OrderMaster{" +
                "orderNo='" + orderNo + '\'' +
                ", shopId=" + shopId +
                ", buyerId=" + buyerId +
                ", buyerName='" + buyerName + '\'' +
                ", buyerPhone='" + buyerPhone + '\'' +
                ", buyerEmail='" + buyerEmail + '\'' +
                ", payment=" + payment +
                ", paymentType=" + paymentType +
                ", orderStatus=" + orderStatus +
                ", paymentTime=" + paymentTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", refuseReason='" + refuseReason + '\'' +
                ", getKey='" + getKey + '\'' +
                '}';
    }
}