package com.wrq.pojo;

public class PageSize {
    private Integer id;

    private String sizeType;

    private Integer shopId;

    private String variable;

    public PageSize(Integer id, String sizeType, Integer shopId, String variable) {
        this.id = id;
        this.sizeType = sizeType;
        this.shopId = shopId;
        this.variable = variable;
    }

    public PageSize() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSizeType() {
        return sizeType;
    }

    public void setSizeType(String sizeType) {
        this.sizeType = sizeType == null ? null : sizeType.trim();
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable == null ? null : variable.trim();
    }
}