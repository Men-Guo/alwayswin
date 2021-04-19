package com.example.alwayswin.entity;

import java.sql.Timestamp;

/**
 * @ClassName: ProductStatus
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */
public class ProductStatus {
    private Integer psid;
    private Integer pid;
    private double price;
    private String status;
    private Timestamp endTime;

    public ProductStatus() {
        psid = 0;
        pid = 0;
        price = 0.0;
        status = "";
        endTime = new Timestamp(0);
    }

    public ProductStatus(Integer psid, Integer pid, double price, String status, Timestamp endTime) {
        this.psid = psid;
        this.pid = pid;
        this.price = price;
        this.status = status;
        this.endTime = endTime;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getPsid() {
        return psid;
    }

    public void setPsid(Integer psid) {
        this.psid = psid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
