package com.example.alwayswin.entity;

import java.sql.Timestamp;
import java.util.List;

public class ProductPreview {
    private Integer pid;
    private Integer uid;
    private String title;
    private Timestamp endTime;
    private double autoWinPrice;
    private ProductStatus productStatus;
    private Figure thumbnail;

    public ProductPreview(Integer pid, Integer uid, String title, Timestamp endTime, double autoWinPrice, ProductStatus productStatus, Figure thumbnail) {
        this.pid = pid;
        this.uid = uid;
        this.title = title;
        this.endTime = endTime;
        this.autoWinPrice = autoWinPrice;
        this.productStatus = productStatus;
        this.thumbnail = thumbnail;
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

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public Figure getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Figure thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "ProductPreview{" +
                "pid=" + pid +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", endTime=" + endTime +
                ", autoWinPrice=" + autoWinPrice +
                ", productStatus=" + productStatus +
                ", thumbnail=" + thumbnail +
                '}';
    }
}
