package com.wrq.pojo;

public class Bonus {
    private Integer id;

    private Integer shopId;

    private String threshold;

    private String bonus;

    private String description;

    public Bonus(Integer id, Integer shopId, String threshold, String bonus, String description) {
        this.id = id;
        this.shopId = shopId;
        this.threshold = threshold;
        this.bonus = bonus;
        this.description = description;
    }

    public Bonus() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold == null ? null : threshold.trim();
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus == null ? null : bonus.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}