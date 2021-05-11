package com.example.alwayswin.entity;

import java.sql.Timestamp;

/**
 * @ClassName: Bidding
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */
public class Bidding {
    private Integer bid;
    private Integer uid;
    private Integer pid;
    private double offer;
    private Timestamp createTime;

    private ProductPreview productPreview;
    private User user;

    public Bidding() {
        bid = 0;
        uid = 0;
        pid = 0;
        offer = 0.0;
        createTime = new Timestamp(0);
        productPreview = new ProductPreview();
        user = new User();
    }

    public Bidding(Integer bid, Integer uid, Integer pid, double offer, Timestamp createTime) {
        this.bid = bid;
        this.uid = uid;
        this.pid = pid;
        this.offer = offer;
        this.createTime = createTime;
        productPreview = new ProductPreview();
        user = new User();
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public double getOffer() {
        return offer;
    }

    public void setOffer(double offer) {
        this.offer = offer;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public ProductPreview getProductPreview() {
        return productPreview;
    }

    public void setProductPreview(ProductPreview productPreview) {
        this.productPreview = productPreview;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Bidding{" +
                "bid=" + bid +
                ", uid=" + uid +
                ", pid=" + pid +
                ", offer=" + offer +
                ", createTime=" + createTime +
                ", product=" + productPreview.toString() +
                ", user=" + user.toString() +
                '}';
    }
}
