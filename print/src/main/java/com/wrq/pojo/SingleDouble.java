package com.wrq.pojo;

public class SingleDouble {
    private Integer id;

    private String pageType;

    private Integer shopId;

    private String variable;

    public SingleDouble(Integer id, String pageType, Integer shopId, String variable) {
        this.id = id;
        this.pageType = pageType;
        this.shopId = shopId;
        this.variable = variable;
    }

    public SingleDouble() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPageType() {
        return pageType;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType == null ? null : pageType.trim();
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