package com.example.alwayswin.entity;

/**
 * @ClassName: Order
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */

import java.sql.Date;
import java.sql.Timestamp;


public class Order {
    private Integer oid;
    private String number;
    private Integer uid;
    private Integer pid;
    // todo: change to string
    private Integer aid;
    private double payment;
    private Timestamp createTime;
    private String status;

    public Order() {
        oid = 0;
        number = "";
        uid = 0;
        pid = 0;
        aid = 0;
        payment = 0.0;
        createTime = new Timestamp(0);
        status = "";
    }

        public Order(Integer oid,String number, Integer uid, Integer pid, Integer aid,
                double payment, Timestamp createTime, String status) {
            this.oid = oid;
            this.number = number;
            this.uid = uid;
            this.pid = pid;
            this.aid = aid;
            this.payment = payment;
            this.createTime = createTime;
            this.status = status;
        }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oid=" + oid +
                ", number='" + number + '\'' +
                ", uid=" + uid +
                ", pid=" + pid +
                ", aid=" + aid +
                ", payment=" + payment +
                ", createTime=" + createTime +
                ", status='" + status + '\'' +
                '}';
    }
}
