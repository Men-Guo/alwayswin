package com.example.alwayswin.entity;

import java.sql.Date;

/**
 * @ClassName: UserInfo
 * @Description:
 * @Author: SQ
 * @Date: 2021-4-19
 */
public class UserInfo {
    private Integer uiid;
    private Integer uid;
    private String portrait;
    private String phone;
    private String email;
    private String gender;    // ('female', 'male', 'other', 'unknown')
    private Date birthday;
    private Date regisDate;
    private double balance;

    public UserInfo() {
        uiid = 0;
        uid = 0;
        portrait = "https://alwayswin-figures.s3.amazonaws.com/icon/default-icon.png";
        phone = "";
        email = "";
        gender = "unknown";
        birthday = new Date(0);
        regisDate = new Date(0);
        balance = 0.0;
    }

    public UserInfo(Integer uiid, Integer uid, String portrait, String phone, String email,
                    String gender, Date birthday, Date regisDate, double balance) {
        this.uiid = uiid;
        this.uid = uid;
        this.portrait = portrait;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
        this.regisDate = regisDate;
        this.balance = balance;
    }

    public Integer getUiid() {
        return uiid;
    }

    public void setUiid(Integer uiid) {
        this.uiid = uiid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getUid() {
        return uid;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getRegisDate() {
        return regisDate;
    }

    public void setRegisDate(Date regisDate) {
        this.regisDate = regisDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "uiid=" + uiid +
                ", uid=" + uid +
                ", portrait='" + portrait + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", regisDate=" + regisDate +
                ", balance=" + balance +
                '}';
    }
}
