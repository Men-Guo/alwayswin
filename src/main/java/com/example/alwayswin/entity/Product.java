package com.example.alwayswin.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Product
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */
public class Product {
    private Integer pid;
    private Integer uid;
    private String title;
    private String description;
    private String cate1;
    private String cate2;
    private String cate3;
    private Timestamp createTime;
    private Timestamp startTime;
    private Timestamp endTime;
    private double startPrice;
    private double autoWinPrice;
    private double reservedPrice;
    private double minIncrement;
    private boolean isPassed;
    private boolean isCanceled;
    private ProductStatus productStatus;
    private List<Figure> figures;
    private Figure thumbnail;

    public Product() {
        pid = 0;
        uid = 0;
        title = "";
        description = "";
        cate1 = "";
        cate2 = "";
        cate3 = "";
        createTime = new Timestamp(0);
        startTime = new Timestamp(0);
        endTime = new Timestamp(0);
        startPrice = 0.0;
        autoWinPrice = 0.0;
        reservedPrice  =0.0;
        minIncrement = 1.0;
        isPassed = true;
        isCanceled = false;
        productStatus = new ProductStatus();
        thumbnail = new Figure();
        figures = new ArrayList<>();
    }

    public Product(Integer pid, Integer uid, String title, String description, String cate1, String cate2, String cate3, Timestamp createTime, Timestamp startTime, Timestamp endTime, double startPrice, double autoWinPrice, double reservedPrice, double minIncrement, boolean isPassed, boolean isCanceled, ProductStatus productStatus, List<Figure> figures, Figure thumbnail) {
        this.pid = pid;
        this.uid = uid;
        this.title = title;
        this.description = description;
        this.cate1 = cate1;
        this.cate2 = cate2;
        this.cate3 = cate3;
        this.createTime = createTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPrice = startPrice;
        this.autoWinPrice = autoWinPrice;
        this.reservedPrice = reservedPrice;
        this.minIncrement = minIncrement;
        this.isPassed = isPassed;
        this.isCanceled = isCanceled;
        this.productStatus = productStatus;
        this.figures = figures;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCate1() {
        return cate1;
    }

    public void setCate1(String cate1) {
        this.cate1 = cate1;
    }

    public void setCate2(String cate2) {
        this.cate2 = cate2;
    }

    public String getCate2() {
        return cate2;
    }

    public String getCate3() {
        return cate3;
    }

    public void setCate3(String cate3) {
        this.cate3 = cate3;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public void setAutoWinPrice(double autoWinPrice) {
        this.autoWinPrice = autoWinPrice;
    }

    public double getAutoWinPrice() {
        return autoWinPrice;
    }

    public double getReservedPrice() {
        return reservedPrice;
    }

    public void setReservedPrice(double reservedPrice) {
        this.reservedPrice = reservedPrice;
    }

    public double getMinIncrement() {
        return minIncrement;
    }

    public void setMinIncrement(double minIncrement) {
        this.minIncrement = minIncrement;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public void setFigures(List<Figure> figures) {
        this.figures = figures;
    }

    public Figure getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Figure thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid=" + pid +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", cate1='" + cate1 + '\'' +
                ", cate2='" + cate2 + '\'' +
                ", cate3='" + cate3 + '\'' +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", startPrice=" + startPrice +
                ", autoWinPrice=" + autoWinPrice +
                ", reservedPrice=" + reservedPrice +
                ", minIncrement=" + minIncrement +
                ", isPassed=" + isPassed +
                ", isCanceled=" + isCanceled +
                ", productStatus=" + productStatus +
                ", figures=" + figures +
                ", thumbnail=" + thumbnail +
                '}';
    }
}

