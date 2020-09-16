package com.wrq.pojo;

import java.math.BigDecimal;

public class Color {
    private Integer id;

    private String colorType;

    private Integer shopId;

    private BigDecimal price;

    public Color(Integer id, String colorType, Integer shopId, BigDecimal price) {
        this.id = id;
        this.colorType = colorType;
        this.shopId = shopId;
        this.price = price;
    }

    public Color() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColorType() {
        return colorType;
    }

    public void setColorType(String colorType) {
        this.colorType = colorType == null ? null : colorType.trim();
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}