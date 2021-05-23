package com.example.alwayswin.entity;

import java.sql.Time;
import java.sql.Timestamp;

public class ProductPreview {
    private Integer pid;
    private Integer uid;
    private String title;
    private String cate1;   // ('camera', 'cell phone', 'accessory', 'computer', 'tablet', 'network hardware', 'tv', 'smart home', 'portable audio', 'car electronics', 'gaming console', 'vr', 'others')
    private Timestamp startTime;
    private Timestamp endTime;
    private double autoWinPrice;
    private double price;
    private String status;
    private String url;
    private UserPreview userPreview;

    public ProductPreview(Integer pid, Integer uid, String title, String cate1, Timestamp endTime, Timestamp startTime, double autoWinPrice, double price, String status, String url, UserPreview userPreview) {
        this.pid = pid;
        this.uid = uid;
        this.title = title;
        this.cate1 = cate1;
        this.endTime = endTime;
        this.startTime = startTime;
        this.autoWinPrice = autoWinPrice;
        this.price = price;
        this.status = status;
        this.url = url;
        this.userPreview = userPreview;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }



    public ProductPreview() {}


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }


    public void setPrice(double price) {
        this.price = price;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCate1() {
        return cate1;
    }

    public void setCate1(String cate1) {
        this.cate1 = cate1;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public double getAutoWinPrice() {
        return autoWinPrice;
    }

    public void setAutoWinPrice(double autoWinPrice) {
        this.autoWinPrice = autoWinPrice;
    }

    public UserPreview getUserPreview() {
        return userPreview;
    }

    public void setUserPreview(UserPreview userPreview) {
        this.userPreview = userPreview;
    }

    @Override
    public String toString() {
        return "ProductPreview{" +
                "pid=" + pid +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", cate1='" + cate1 + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", autoWinPrice=" + autoWinPrice +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", url='" + url + '\'' +
                ", userPreview=" + userPreview +
                '}';
    }
}
