package com.example.alwayswin.entity;

/**
 * @ClassName: Order
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */

import java.sql.Timestamp;


public class Order {
    private Integer oid;
    private String number;  // 8位大写字母数字随机组合
    private Integer uid;
    private Integer pid;
    private String address;
    private double payment;
    private Timestamp createTime;
    private String status;  // ('placed', 'paid', 'shipped', 'received')

    private ProductPreview productPreview;

    public Order() {
        oid = 0;
        number = "";
        uid = 0;
        pid = 0;
        address = "";
        payment = 0.0;
        createTime = new Timestamp(0);
        status = "";
        productPreview = new ProductPreview();
    }

        public Order(Integer oid,String number, Integer uid, Integer pid, String address,
                double payment, Timestamp createTime, String status) {
            this.oid = oid;
            this.number = number;
            this.uid = uid;
            this.pid = pid;
            this.address = address;
            this.payment = payment;
            this.createTime = createTime;
            this.status = status;
            productPreview = new ProductPreview();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public ProductPreview getProductPreview() {
        return productPreview;
    }

    public void setProductPreview(ProductPreview productPreview) {
        this.productPreview = productPreview;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oid=" + oid +
                ", number='" + number + '\'' +
                ", uid=" + uid +
                ", pid=" + pid +
                ", address=" + address +
                ", payment=" + payment +
                ", createTime=" + createTime +
                ", status='" + status + '\'' +
                ", productPreview='" + productPreview.toString() + '\'' +
                '}';
    }
}
